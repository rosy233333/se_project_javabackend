阅读该项目的方法：

只看controller包里的类就好。

如果涉及数据库，就去看repository包里的类。

data_item包里的类，直接不管就好。之后用到了哪个类，再去看。

另：如果涉及与python后端的通信，可以参考`ImageRecogController::handleFileUpload`方法里的写法。