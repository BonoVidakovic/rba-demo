keytool -importcert -alias receiverKeyPair -storetype PKCS12 \
  -keystore receiver_keystore.p12 -file \
  sender_certificate.cer -rfc -storepass password
