# cs211-641-project

## รายชื่อสมาชิก
* 6310400991 ณัฐภรณ์ มีบุญ (github account : Coolfreewerx)
  * เขียนโปรแกรมส่วนของระบบรายการสินค้า ซื้อสินค้า และประวัติการซื้อสินค้า
* 6310401025 ทัศวรรณ จันกรี (github account : thatsawan2002)  
  * เขียนโปรแกรมส่วนของระบบโปรโมชัน รีวิวสินค้า และแก้ไขข้อมูลผู้ใช้
* 6310406337 มณีสร วิจารณกุล (github account : ManeesornV)
  * เขียนโปรแกรมส่วนของระบบผู้ขายสินค้า และจัดการร้านค้าของผู้ดูแลระบบ
* 6310406361 สิทธานนท์ มงคลคำ (github account : isFrince)
  * เขียนโปรแกรมส่วนของระบบสมัครและการเข้าสู่ระบบ จัดการผู้ใช้ของผู้ดูแลระบบ และรายงานความไม่เหมาะสม


## วิธีการติดตั้งหรือรันโปรแกรม
* ดาวน์โหลดโปรแกรมเป็นรูปแบบไฟล์ .zip จาก https://drive.google.com/drive/folders/1dGbHlZa4_i2xFHBelzFuTaZkVwNDE69I?usp=sharing
* หากต้องการรันโปรแกรมให้ ทำการ Extract File .zip กดเข้าไฟล์ cs211_431_project_jar และดับเบิ้ลคลิกที่ไฟล์ cs211-431-project.jar
* หากเกิดปัญหาให้เปิดโปรแกรมผ่าน Command Prompt ใช้คำสั่ง "java -jar cs211-431-project.jar"

## การวางโครงสร้างไฟล์
* ในส่วนของโปรแกรมจะมีการวางโครงสร้างไฟล์เป็นสัดส่วนดังนี้
- 1 ส่วนของ csv-data เป็นโฟลเดอร์ที่เก็บไฟล์ข้อมูล .csv ทั้งหมดของโปรแกรม
- 2 ส่วนของ images เป็นโฟลเดอร์ที่เก็บไฟล์ข้อมูลรูปภาพที่ใช้ใน .csv ทั้งหมดของโปรแกรม
- 3 ส่วนของ src-java เป็นส่วนที่ไฟล์งานของโปรแกรมซึ่งประกอบด้วยส่วนย่อยอีกคือ โฟลเดอร์ controllers, โฟลเดอร์ models, โฟลเดอร์ services และไฟล์ App.java
  ซึ่งเป็นไฟล์ที่กำหนดการตั้งค่าเบื้องต้นก่อนการรันโปรแกรม
- 4 ส่วนของ src-resources เป็นโฟลเดอร์ที่เก็บไฟล์ UI ทั้งหมดของโปรแกรม รวมถึงรูปภาพ ไฟล์ .css และ font ที่ใช้ภายใน UI

## ตัวอย่างข้อมูลผู้ใช้ระบบ
* ตัวอย่างข้อมูลผู้ใช้ระบบ
* 1 ข้อมูลผู้ใช้ระบบสำหรับ Admin
   * Username :  Nonattis
   * Password  :  Fz013925
* 2 ข้อมูลผู้ใช้ระบบสำหรับ User
   * Username :  Xiao
   * Password  :  Xiao1234
* 3 ข้อมูลผู้ใช้ระบบสำหรับ Seller
   * Username :  coolfreewerx
   * Password  :  Stamp8239

## สรุปสิ่งที่พัฒนาแต่ละครั้งที่นำเสนอความก้าวหน้าของระบบ
* ครั้งที่ 1 (29/07/2021)
  * design - seller.fxml / seller order list page / seller's delivery page / seller application page (พัฒนาโดย ณัฐภรณ์ มีบุญ)
  * design - basket.fxml / payment page / home / Personal information page (พัฒนาโดย ทัศวรรณ จันกรี)
  * design - detailbook.fxml / home / detailbook page / basket page (พัฒนาโดย มณีสร วิจารณกุล)
  * design - register.fxml / login / about us page / Personal information page (พัฒนาโดย สิทธานนท์ มงคลคำ)
* ครั้งที่ 2 (03/09/2021)
  * BookDetail (show book detail from csv) /Book /Home (show book item) (พัฒนาโดย ณัฐภรณ์ มีบุญ)
  * login.fxml/headController (พัฒนาโดย ทัศวรรณ จันกรี)
  * SellerStock/Stock/DetailUser (พัฒนาโดย มณีสร วิจารณกุล)
  * login system/register system/About Us Information (พัฒนาโดย สิทธานนท์ มงคลคำ)
* ครั้งที่ 3 (30/09/2021)
  * BookDetailController /BookShopDetailController /HomeController /ItemController /OrderController /StockController /TypeBookController (พัฒนาโดย ณัฐภรณ์ มีบุญ)
  * AccountDetailController /EditAddressController /EditInformationController /ส่วนหัวเพจ (พัฒนาโดย ทัศวรรณ จันกรี)
  * ApplyBookController /ApplyToBeASellerController /EditBookController /OrderListController /SellerStockController (พัฒนาโดย มณีสร วิจารณกุล)
  * LoginController /RegisterController /UserListForAdminController /AboutUsChoiceController /AboutUsController /AdminInfoChoiceController (พัฒนาโดย สิทธานนท์ มงคลคำ)  