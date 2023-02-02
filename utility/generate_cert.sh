keytool -exportcert -alias senderKeyPair -storetype PKCS12 \
  -keystore sender_keystore.p12 -file \
  sender_certificate.cer -rfc -storepass password
