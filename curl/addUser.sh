curl  --data "<?xml version='1.0' encoding='UTF-8' standalone='yes'?><AddUserRequest><ScreenName>sunnypanjwani</ScreenName><FirstName>sunny</FirstName><LastName>panjwani</LastName><Email>sunnypanjwani86@gmail.com</Email><AddressLine1>test1</AddressLine1><AddressLine2>test2</AddressLine2><AddressLine3>test3</AddressLine3><AddressCity>city</AddressCity><AddressState>state</AddressState><AddressCountry>usa</AddressCountry><AddressZip>12345</AddressZip></AddUserRequest>" --header "Content-Type: application/xml"  http://localhost:8080/stars_service-0.0.1/v1/stars/addUser/
