@REM ����˽����Կ���ɹ�Կ
keytool -export ^
	-alias tomcat ^
	-keystore www.2tiger.site_keystore.jks ^
	-storetype PKCS12 ^
	-storepass xxooxxoo ^
	-rfc ^
	-file tomcat-PublicKey.cer
