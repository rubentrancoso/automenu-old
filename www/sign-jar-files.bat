keytool -genkey -keystore automenu-certificate -alias automenu
jarsigner -keystore automenu-certificate automenu-demo.jar automenu
jarsigner -keystore automenu-certificate automenu.jar automenu
jarsigner -keystore automenu-certificate commons-jxpath-1.3.jar automenu
