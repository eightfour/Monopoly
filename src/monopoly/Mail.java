package monopoly;



	 
	import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

		/**
		 * 
		 * @author Thomas Fregin
		 * Mail Klasse zum verschicken von Mails 
		 * bis zu 2 Dateien k�nnen verschickt werden
		 */
		public class Mail extends Thread implements IDatenzugriff{
			
			private Properties p;
			
			private class MailAuthenticator extends Authenticator{
				private String user, password;
				public MailAuthenticator(){
					this.user=p.get("mail.smtp.user").toString();
					this.password=p.get("mail.smtp.password").toString();
				}
				@Override
				public PasswordAuthentication getPasswordAuthentication(){
					return new PasswordAuthentication(user, password);
				}
			}
			
			/**
			 * Zu verschickende Mail vom Gruppenaccount �berpr�fung der Zielmail durch Regex
			 * @param an	Email-Adresse des Empf�ngers 
			 * @param betreff betreff der zu verschickenden Mail
			 * @param text	  Text der zu vershcickenden Mail
			 * @param anhangPfad1 Pfad f�r die erste Datei
			 * @param anhangName1 Name der ersten datei
			 * @param anhangPfad2 Pfad f�r die erste Datei
			 * @param anhangName2 Name der ersten datei
			 */
		public Mail(String an, String betreff, String text, 
				String anhangPfad1, String anhangName1,
				String anhangPfad2, String anhangName2){
			if((an==null)||(an.length()==0))return;
			p = new Properties();
			p.put("mail.smtp.host","smtp.gmail.com");
			p.put("mail.smtp.user","MonopolyMTBT.A1@gmail.com");
			p.put("mail.smtp.password","MonopolyMTBTA1");
			p.put("mail.smtp.socketFactory.port","465");
			p.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			p.put("mail.smtp.auth","true");
			p.put("mail.smtp.port","465");
			p.put("von","MonopolyMTBTA1");
			p.put("an",an);
			p.put("betreff", betreff);
			p.put("text", text);
			
			if(anhangPfad1==null)
				p.put("anhangPfad1", "");
			else
				p.put("anhangPfad1", anhangPfad1);
			if(anhangName1==null)
				p.put("anhangName1","");
			else
				p.put("anhangName1", anhangName1);
			if(anhangPfad2==null)
				p.put("anhangPfad2", "");
			else
				p.put("anhangPfad2", anhangPfad2);
			if(anhangName2==null)
				p.put("anhangName2", "");
			else
				p.put("anhangName2", anhangName2);
			this.start();
		}
		/**
		 *  Anh�ngen vom Text, Betreff, dem Anhang und regelung des Zugangs 
		 * @return erfolgreiches Senden der Mail oder Ausgabe der Fehlermeldung
		 */
		@Override
		public void run(){
			try{
				System.out.println("Starte Mailing an "+p.getProperty("an"));
				MailAuthenticator auth = new MailAuthenticator();
				Session session= Session.getDefaultInstance(p, auth);
				Message msg= new MimeMessage(session);
				msg.setFrom(new InternetAddress(p.getProperty("von")));
				msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(p.getProperty("an"),false));
				msg.setSubject(p.getProperty("betreff"));
				MimeBodyPart bodyNachricht = new MimeBodyPart();
				bodyNachricht.setText(p.getProperty("text"));
				Multipart body = new MimeMultipart();
				body.addBodyPart(bodyNachricht);
				for (int i=1;i<=2;i++){
					if((!p.getProperty("anhangPfad"+i).equals(""))&&(!p.getProperty("anhangName"+i).equals(""))){
						MimeBodyPart bodyAnhang = new MimeBodyPart();
						DataSource source = new FileDataSource(p.getProperty("anhangPfad"+i));
						bodyAnhang.setDataHandler(new DataHandler(source));
						bodyAnhang.setFileName(p.getProperty("anhangName"+i));
						body.addBodyPart(bodyAnhang);
					}
				}
				msg.setContent(body);
				msg.setSentDate(new Date());
				Transport.send(msg);
				 System.out.println("Mailing an "+p.getProperty("an")+" erfolgreich beendet.");
			}
			catch( Exception e){
				System.out.println("Mailing an "+p.getProperty("an")+" Fehlgeschlagen:");
				e.printStackTrace();
			}
		}
		@Override
		public void laden() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void MailSenden(String an, String betreff, String text, String anhangPfad1, 
				String anhangName1,String anhangPfad2, String anhangName2) {
				new Mail(an, betreff, text, anhangPfad1, anhangName1,anhangPfad2, anhangName2);
		}
		@Override
		public Spiel laden(String pfad) {
			return null;
			
		}
		@Override
		public void speichern(String string) {
			// TODO Auto-generated method stub
			
		}
		
		
		
	

	
}
