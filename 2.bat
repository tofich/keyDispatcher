mkdir folderName
"C:\\Program Files\\OpenSSL-Win64\\bin\\openssl.exe" req -out "folderName\server123.csr" -new -newkey 2048 -nodes -keyout "folderName\server321.key" -config "C:\\Program Files\\OpenSSL-Win64\\bin\\openssl.cfg" -subj "/C=US/ST=Utah/L=Lehi/O=Your Company, Inc./OU=IT/CN=yourdomain.com"
