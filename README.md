# utilities
This is a simple utility for any finance freak, it requires you to set up, emails whenever a transaction occurs on your credit card or bank.

This utility will scan your email (you'll need to provide credentials, obviously)

And can send you a text message/email at the end of the day, with the total spent. it's  a standalone application and you need to run it locally.

I tested with gmail, and windows/mac os's it seems to work okay.

here's a Sample config which it will need to run. There's a list of jar files it need too, I will updated the readme in coming days, otherwise it's setup as a maven project, you should be able to get the dependencies when you download this project.



---config----


#standard Gmail properties (These are defaulted in app as well)
mail.smtp.socketFactory.class=javax.net.ssl.SSLSocketFactory
mail.smtps.host=smtp.gmail.com
mail.smtp.socketFactory.fallback=false
mail.smtp.port=465
mail.smtp.socketFactory.port=465
mail.smtps.auth=true




email.user.name=xxx@gmail.com
email.password=xxxx
#outgoing email or Text, at this time only one
email.sms.address=123123@txt.att.net 

#1 means current day, default setting
num.of.days.email.check=2

#if you don't specify this, it defaults to 1 
total.accounts=1

#As requested, we are notifying you that on MAY 22, 2016 at CVS/PHARMACY # a pending authorization or purchase in the amount of $6.98 was placed or charged on your Capital One® VISA SIGNATURE account.
account.2.name=Visa
account.2.email.from=visaCard@bankofAmerica.com
account.2.date.regex=[a-zA-Z]{3}\\s[0-9]{1,2}, [0-9]{4} )
account.2.amount.regex=\\$\\d*\\.\\d*
############this is optional, better to provide, before and after, the system will extract merchant name itself.
#############account.1.mrchnt.regex=\\sat\\s(.)+\\sa pending
account.2.mrchnt.regex.start=at
account.2.mrchnt.regex.end=a pending
#
