#!/bin/bash 
 
MMDDHHmmSS=`/bin/date +%m%d%H%M%S` 
 
REQUEST='<?xml version="1.0" encoding="UTF-8"?>
<AddUserRequest>
   <ScreenName>Ashu1</ScreenName>
   <FirstName>Ednitpreet</FirstName>
   <LastName>Ghai</LastName>
   <Email>abc@xyz.com</Email>
   <Password>pass</Password>
   <Sex>Male</Sex>
</AddUserRequest>'
 
URL=http://localhost:8080/stars_service-0.0.1/v1/stars/user/addUser
 
echo curl -v -H "Content-Type: application/xml" -k -d "$REQUEST" $URL 
curl -v -H "Content-Type: application/xml" -k -d "$REQUEST" $URL -L 

echo
