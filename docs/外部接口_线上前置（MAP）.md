
#目的

该文档是营销账户平台·线上业务对外开放接口文档，用于对外开放线上开卡、线上下载产品列表的能力。


#接口

##线上系列接口

###获取短信验证码接口（实现但暂时不提供）

在线上开卡前，第三方应用系统可以使用该接口向需要开卡的手机号码发送短信验证码，用于验证手机号码的真实性。

其中中经汇通将会为第三方应用系统分配发送短信的账号和密码，第三方应用系统通过账号和密码进行获取短信验证码。

1）请求说明

http请求方式: post

    http://IP:PORT/front/online/get_smscode



POST数据格式：JSON

    {
        "channelNo":"C001", "timeStamp":"TIMESTAMP", "nonce":"NONCE", "signature":"SIGNATURE", "mobileNum":"13912345678"
    }  

参数|必须|说明
-------|------|-------
channelNo|是|中经为第三方帐户分配的渠道编号
timeStamp|是|时间戳
nonce|是|随机数
signature|是|签名值，MD5(按值的字典顺序排列组合成字符串(channelNo, channelKey, nonce, timeStamp))，channelKey为中经汇通分配，参与MD5运算
mobileNum|是|需要验证码的手机号码


2）返回说明

正常时的返回JSON数据包示例：

    {"errcode":0,"errmsg":"已经发送短信验证码"}

错误时的JSON数据包示例：

    {"errcode":10000,"errmsg":"发送短信验证码失败，其它原因"}

###线上开卡接口

该接口仅提供给**联动优势公司和深圳麦圈公司使用**，用于线上开卡。为了保证安全性，要求线上前置分别对联动优势和麦圈公司进行IP鉴权。

1）请求说明


http请求方式: post

    http://IP:PORT/front/online/open?channel=C001


POST数据格式：JSON

    {
        "ID":"515411244445444444X", "name":"李四", "mobileNum":"13912345678",  "channelOrderId":"channel1234567890", "productId":"1010", "signature":"SIGNATURE"
    }


参数|必须|说明
------|------|-------
channel|是|渠道编码，中经汇通分配
ID|是|身份证号，要求以渠道秘钥，采用3DES加密后base64编码
name|是|姓名，要求以渠道秘钥，采用3DES加密后base64编码
mobileNum|是|开卡手机号码，要求以渠道秘钥，采用3DES加密后base64编码
smsCode|是|通过短信获取到的验证码
channelOrderId|是|渠道自定义订单号，用于异常时，查询开卡结果
productId|是|产品编号
signature|是|签名值，MD5(按字典排序组合(ID+name+mobileNum+channelOrderId+productId+渠道秘钥))

2）返回说明

正常时的返回JSON数据包示例：

    {
        "errcode":0,"errmsg":"用户开卡成功",
        "orderId":"12345678901234567890",
        "card":{
            "accountId":"1234123412341234",
            "amount":400000,
            "startTime":"2015-01-01 00:00:00",
            "endTime":"2015-05-31 24:00:00",
            "status":"正常"
        }
    }

错误时的JSON数据包示例：

    {
        "errcode":10000,"errmsg":"用户开卡失败，其它错误"
    }

参数|必须|说明
----|----|----
orderId|是|MAP返回的订单号
accountId|是|卡账号
amount|是|卡面额，单位：分
startTime|是|有效期-开始日期
endTime|是|有效期-结束日期
status|是|状态，正常、冻结



###查询开分期卡结果接口

当开卡出现异常的时候，第三方应用系统可以使用该接口，检查开卡是否成功。

1）请求说明


http请求方式: post

    http://IP:PORT/front/online/get?channel=C001


POST数据格式：JSON

    {
        "channelOrderId":"channel1234567890", "productId":"1010"
    }


参数|必须|说明
------|------|-------
channel|是|渠道编码
channelOrderId|是|需要查询开分期卡结果的渠道订单号，由于系统异常，第三方应用系统可能未接收到我方后台返回的订单号，因此采用第三方订单号查询，因此请第三方系统保证该值得唯一性。
productId|是|产品编号


2）返回说明

正常时的返回JSON数据包示例：

    {
        "orderId":"12345678901234567890",
        "card":{
            "accountId":"1234123412341234",
            "amount":400000,
            "startTime":"2015-01-01 00:00:00",
            "endTime":"2015-05-31 24:00:00",
            "status":"正常"
        }
    }

错误时的JSON数据包示例：

    {
        "errcode":10000,"errmsg":"没有查找到对应的卡订单，其它错误"
    }

参数|必须|说明
----|----|----
orderId|是|开卡的订单号
accountId|是|分期卡卡号
amount|是|卡面额，单位：分
startTime|是|有效期-开始日期
endTime|是|有效期-结束日期
status|是|状态，正常、冻结

###查询我的卡接口

第三方App通过此接口查询用户所开通的卡

1）请求说明


http请求方式: post

    http://IP:PORT/front/online/listCards


POST数据格式：JSON

    {
        "pageSize":10,"pageIndex":1,
        "mobileNum":"13912345678",
        "startDate":"2015-07-01",
        "endDate":"2015-07-10",
        "types":["VCA","DCA","OCA"],
        "status":["END","OK#","NOP","FRZ"]
    }


参数|必须|说明
------|------|-------
mobileNum|是|手机号码
startDate|否|开卡时间时间段,格式yyyy-MM-dd
endDate|否|开卡时间时间段,格式yyyy-MM-dd
types|否|卡类型,VCA-容量卡,DCA-折扣卡,OCA-分期卡,为空为所有
status|否|卡状态,"END" 结束,"OK#" 正常,"NOP" 未开通,"FRZ" 冻结(不能使用)

2）返回说明

正常时的返回JSON数据包示例：

    {
        "orderId":"12345678901234567890",
        "card":{
            "accountId":"1234123412341234",
            "amount":400000,
            "startTime":"2015-01-01 00:00:00",
            "endTime":"2015-05-31 24:00:00",
            "status":"OK#",
            "description":"300元加油卡不限油品限厦门地区使用"
            "ext":{}
        }
    }

错误时的JSON数据包示例：

    {
        "errcode":10000,"errmsg":"没有查找到对应的卡订单，其它错误"
    }

参数|必须|说明
----|----|----
orderId|是|开卡的订单号
accountId|是|分期卡卡号
amount|是|卡面额，单位：分
startTime|是|有效期-开始日期
endTime|是|有效期-结束日期
status|是|状态，OK#正常、FRZ冻结,END 结束,NOP 未开通
description|否|卡描述
ext|否|卡的额外属性
