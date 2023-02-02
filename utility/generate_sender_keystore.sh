keytool -genkeypair -alias senderKeyPair -keyalg RSA -keysize 2048 \
  -dname "CN=RBA_DEMO" -validity 365 -storetype PKCS12 \
  -keystore sender_keystore.p12 -storepass password
