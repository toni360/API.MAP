
#目的

该文档是营销账户平台·线下业务对外开放接口文档，用于对外开放线下开卡、线下消费、线下交易撤销、线下交易冲正和下载产品列表的能力。


#接口

##线下前置接口

###线下开卡接口

用户在中经汇通POS上开卡，包括分期卡和其他卡。

1）请求说明

http请求方式: post

    http://IP:PORT/front/offline/open_card

>note:
>对应交易类型为：开卡

POST数据格式：JSON

    {
        "ID":"515411244445444444X", 
        "mobileNum":"13912345678", 
        "referrer":"12345678",
        "sellerId":"SH01",
        "storeId":"ST01",
        "terminalId":"POS01",
        "batchNo":"1234567",
        "operatorId":"zhangsan",
        "terminalOrderId":"1234567890",
        "productId":"1010",
        "paySum":100000
    }


参数|必须|说明
------|------|-------
ID|是|开卡人身份证号
mobileNum|是|开卡人手机号码
referrer|否|推荐人代码,线下营业厅销售人员
sellerId|是|商户编码
storeId|是|门店编码
terminalId|是|终端编码
operatorId|是|操作员编码
batchNo|是|批次号
terminalOrderId|是|终端流水号，非唯一。当出现异常时，可以根据terminalId+ batchNo+ terminalOrderId唯一确定交易订单，进行后续的处理
productId|是|产品编号，通过产品下载接口获取产品编号
paySum|否|支付金额，该参数是因为线下开卡卡允许拆单付款，需要支付金额来合并订单

2）返回说明

正常时的返回JSON数据包示例：

    {
        "errcode":0,"errmsg":"用户开卡成功",
        "order":{
            "orderId":"12345678901234567890",
            "mobileNum":"13912345678", 
            "sellerId":"SH01",
            "storeId":"ST01",
            "terminalId":"POS01",
            "operatorId":"001",
            "batchNo":"1234567",
            "terminalOrderId":"1234567890",
            "remark":""
        }
        "request":"RESQUEST"
    }

错误时的JSON数据包示例：

    {
        "errcode":10000,"errmsg":"用户开卡失败，其它错误",
        "request":"RESQUEST"
    }


参数|必须|说明
----|----|----
orderId|是|后台返回的订单号
mobileNum|是|开卡人手机号码
sellerId|是|商户编码
storeId|是|是|门店编码
terminalId|是|终端编码
operatorId|是|操作员编码
batchNo|是|批次号
terminalOrderId|是|终端流水号，非唯一。当出现异常时，可以根据terminalId+ batchNo+ terminalOrderId唯一确定交易订单，进行后续的处理
remark|是|备注
request|是|原请求报文，用于POSP防止串包

###线下开卡撤销接口

当发生购卡失败或者异常后，调用该接口将已经开卡成功的进行撤销。

1）请求说明

http请求方式: post

    http://IP:PORT/front/offline/repeal_card


POST数据格式：JSON

    {
        "sellerId":"SH01",
        "storeId":"ST01",
        "terminalId":"POS01",
        "batchNo":"1234567",
        "operatorId":"zhangsan",
        "terminalOrderId":"1234567890",
    }


参数|必须|说明
------|------|-------
sellerId|是|商户编码
storeId|是|门店编码
terminalId|是|终端编码
operatorId|是|操作员编码
batchNo|是|批次号
terminalOrderId|是|需要撤销的终端流水号

2）返回说明

正常时的返回JSON数据包示例：

    {
        "errcode":0,"errmsg":"用户线下开卡撤销成功",
        "order":{
            "orderId":"12345678901234567890",
            "mobileNum":"13912345678", 
            "sellerId":"SH01",
            "storeId":"ST01",
            "terminalId":"POS01",
            "operatorId":"001",
            "batchNo":"1234567",
            "terminalOrderId":"1234567890",
            "remark":""
        }
        "request":"RESQUEST"
    }

错误时的JSON数据包示例：

    {
        "errcode":10000,"errmsg":"用户线下开卡撤销失败，其它错误",
        "request":"RESQUEST"
    }


参数|必须|说明
----|----|----
orderId|是|后台返回的订单号
mobileNum|是|开卡人手机号码
sellerId|是|商户编码
storeId|是|是|门店编码
terminalId|是|终端编码
operatorId|是|操作员编码
batchNo|是|批次号
terminalOrderId|是|终端流水号，非唯一。当出现异常时，可以根据terminalId+ batchNo+ terminalOrderId唯一确定交易订单，进行后续的处理
remark|是|备注
request|是|原请求报文，用于POSP防止串包

###线下支付接口

该接口仅供中经汇通POSP调用，用于在中经汇通POS机上完成线下支付。

1）请求说明


http请求方式: post

    http://IP:PORT/front/offline/pay

>note:
>对应交易类型为：消费


POST数据格式：JSON

    {
        "order":{
            "consumerId":"13912345678",
            "paymentCodeDES":"231EEREWQR",
            "sum":5000,
            "sellerId":"SH12345",
            "storeId":"ST12345",
            "terminalId":"ZJ000001",
            "operatorId":"zhangsan",
            "batchNo":"123123123122132",
            "terminalOrderId":"1234567890123456789",
            "productName":"0号柴油",
            "productPrice":500,
            "productQuantity":"1.55",
            "remark":""
        }
    }


参数|必须|说明
------|------|-------
consumerId|是|消费号码，可以是手机号码，也可以是具体的卡号，即mobileNum或accountId，POS可以通过扫码、NFC等方式读取消费者号码，并使用该账户消费。如果消费号码是手机号码，根据系统规则选择合适的账户进行消费，默认规则是优先选择折扣卡账户消费，其次是现金账户消费
paymentCodeDES|是|加密的支付密码
sum|是|消费金额，单位：分
sellerId|是|商户编码
storeId|是|门店编码
terminalId|是|终端编码
operatorId|是|操作员编码
batchNo|是|批次号
terminalOrderId|是|终端流水号
productName|否|商品编码，比如0号柴油，仅作记录
productPrice|否|商品价格，比如5.00元/升，仅作记录
productQuantity|否|商品数量，比如1.55升，仅作记录
remark|否|备注

2）返回说明

正常时的返回JSON数据包示例：

    {
        "errcode":0,"errmsg":"支付成功", 
        "order":{
            "orderId":"ud12345678901234567890"
            "consumerId":"13912345678",
            "sum":5000,
            "payTime":"2015-01-02 12:12:00"
            "sellerId":"SH12345",
            "storeId":"ST12345",
            "terminalId":"ZJ000001",
            "operatorId":"zhangsan",
            "batchNo":"123123123122132",
            "terminalOrderId":"1234567890123456789",
            "productName":"0号柴油",
            "productPrice":500,
            "productQuantity":"1.55",
            "remark":"",
            "status":"支付成功"
        },
        "request":"RESQUEST"
    }

错误时的JSON数据包示例：

    {
        "errcode":10000,"errmsg":"支付失败，其它错误",
        "request":"RESQUEST"
    }

参数|必须|说明
----|----|----
orderId|是|后台返回的交易订单号
consumerId|是|消费号码
sum|是|消费金额
payTime|是|交易时间
status|是|状态
sellerId|是|商户编码
storeId|是|门店编码
terminalId|是|终端编码
operatorId|是|操作员编码
batchNo|是|批次号
terminalOrderId|是|终端流水号
productName|否|商品编码，比如0号柴油
productPrice|否|商品价格，比如5.00元/升
productQuantity|否|商品数量，比如1.55升
remark|否|备注
request|是|原请求报文

###线下交易撤销接口

该接口仅供中经汇通POSP调用，用于营业员在中经汇通POS机上完成撤销功能，**必须在原POS机上执行撤销操作**。

1）请求说明


http请求方式: post

    http://IP:PORT/front/offline/pay_cancel

>note:
>对应交易类型为：消费撤销


POST数据格式：JSON

    {
        "order":{
            "orderId":"12345678900987654321",
            "sellerId":"SH12345",
            "storeId":"ST12345",
            "terminalId":"ZJ000001",
            "operatorId":"zhangsan",
            "batchNo":"123123123122132",
            "terminalOrderId":"1234567890123456789",
            "remark":""
        }
    }


参数|必须|说明
------|------|-------
orderId|是|需要撤销的终端流水号
sellerId|是|商户编码
storeId|是|门店编码
terminalId|是|终端编码
operatorId|是|操作员编码
batchNo|是|批次号
terminalOrderId|是|终端流水号
remark|否|备注

2）返回说明

正常时的返回JSON数据包示例：

    {
        "errcode":0,"errmsg":"交易撤销成功", 
        "order":{
            "orderId":"ud12345678901234567890"
            "payTime":"2015-01-02 12:12:00"
            "sellerId":"SH12345",
            "storeId":"ST12345",
            "terminalId":"ZJ000001",
            "operatorId":"zhangsan",
            "batchNo":"123123123122132",
            "terminalOrderId":"1234567890123456789",
            "remark":""
            "status":"消费撤销成功"
        }
        "request":"RESQUEST"
    }

错误时的JSON数据包示例：

    {
        "errcode":10000,"errmsg":"交易撤销失败，其它错误",
        "request":"RESQUEST"
    }

参数|必须|说明
----|----|----
orderId|是|后台返回的交易撤销的订单号
payTime|是|交易时间
sellerId|是|商户编码
storeId|是|门店编码
terminalId|是|终端编码
operatorId|是|操作员编码
batchNo|是|批次号
terminalOrderId|是|终端流水号
remark|否|备注
status|是|状态
request|是|原请求报文


###线下交易冲正接口

该接口仅供中经汇通POSP调用，用于中经汇通POS机在处理异常后自动发起的冲正功能。

1）请求说明


http请求方式: post

    http://IP:PORT/front/offline/pay_undo

>note:
>对应交易类型为：消费冲正


POST数据格式：JSON

    {
        "order":{
            "sellerId":"GM01",
            "storeId":"ST01",
            "terminalId":"POS12345",
            "operatorId":"zhangsan",
            "terminalOrderId":"1234567890",
            "batchNo":"123123123122132",
            "remark":""
        }
    }


参数|必须|说明
------|------|-------
sellerId|是|商户编码
storeId|是|门店编码
terminalId|是|终端编码
operatorId|是|操作员编码
batchNo|是|批次号
terminalOrderId|是|需要冲正的终端流水号，冲正是POS机自动触发的行为，冲正时产生的终端流水号就是需要冲正的终端流水号（两个流水号是一样的）
remark|否|备注

2）返回说明

正常时的返回JSON数据包示例：

    {
        "errcode":0,"errmsg":"消费冲正成功", 
        "order":{
            "orderId":"ud12345678901234567890"
            "consumerId":"13912345678",
            "sum":5000,
            "payTime":"2015-01-02 12:12:00"
            "sellerId":"SH12345",
            "storeId":"ST12345",
            "terminalId":"ZJ000001",
            "operatorId":"zhangsan",
            "batchNo":"123123123122132",
            "terminalOrderId":"1234567890123456789",
            "productName":"0号柴油",
            "productPrice":500,
            "productQuantity":"1.55",
            "remark":"",
            "status":"支付失败，冲正成功"
        },
        "request":"RESQUEST"
    }

错误时的JSON数据包示例：

    {
        "errcode":10000,"errmsg":"消费冲正失败，其它错误",
        "request":"RESQUEST"
    }

参数|必须|说明
----|----|----
orderId|是|后台返回的消费冲正的订单号
consumerId|是|消费号码
sum|是|消费金额，单位：分
payTime|是|交易时间
status|是|状态
sellerId|是|商户编码
storeId|是|门店编码
terminalId|是|终端编码
operatorId|是|操作员编码
batchNo|是|批次号
terminalOrderId|是|终端流水号
productName|否|商品编码，比如0号柴油
productPrice|否|商品价格，比如5.00元/升
productQuantity|否|商品数量，比如1.55升
remark|否|备注
request|是|原请求报文


###线下查询余额接口

该接口仅供中经汇通POSP调用，用于营业员在中经汇通POS机上完成查询余额功能。

1）请求说明


http请求方式: post

    http://IP:PORT/front/offline/get_account_info


POST数据格式：JSON

    {
        "query":{
            "consumerId":"13912345678",
            "paymentCodeDES":"231EEREWQR",
            "sellerId":"GM01",
            "storeId":"ST01",
            "terminalId":"POS12345",
            "operatorId":"zhangsan",
            "terminalOrderId":"1234567890",
            "batchNo":"123123123122132",
            "remark":""
        }
    }


参数|必须|说明
------|------|-------
consumerId|否|需要查询的消费号码，如果是手机号码，查询该手机号码对应的现金账户余额，如果是卡号，则查询卡号的余额
paymentCodeDES|是|加密的支付密码
sellerId|是|商户编码
storeId|是|门店编码
terminalId|是|终端编码
operatorId|是|操作员编码
batchNo|是|批次号
terminalOrderId|是|终端流水号
remark|否|备注

2）返回说明

正常时的返回JSON数据包示例：

    {
        "errcode":0,"errmsg":"查询余额成功", 
        "card":{
            "accountId":"1010123412341234",
            "balance":300000,
            "status":"正常",
            "remark":""
        },
        "request":"RESQUEST"
    }

错误时的JSON数据包示例：

    {
        "errcode":10000,"errmsg":"查询余额失败，其它错误",
        "request":"RESQUEST"
    }

参数|必须|说明
----|----|----
accountId|是|账户号码
balance|是|当前可用金额，单位：分
status|是|状态
remark|是|备注
request|是|原请求报文


###线下产品下载接口

用于POS机下载产品列表。

1）请求说明

http请求方式: post

    http://IP:PORT/front/offline/download_product


POST数据格式：JSON

    {
        "sellerId":"SH01",
        "storeId":"ST01",
        "terminalId":"POS01",
        "batchNo":"1234567",
        "terminalOrderId":"1234567890",
    }


参数|必须|说明
------|------|-------
sellerId|是|商户编码
storeId|是|是|门店编码
terminalId|是|终端编码
batchNo|是|批次号
terminalOrderId|是|终端流水号


2）返回说明

正常时的返回JSON数据包示例：

    {
        "product":[
            {
                "productId":"1010",
                "productName":"乐驾包5000", 
                "productShortName":"乐驾包5000", 
                "productPrice":450000,
                "remark":""
            },
            {
                "productId":"1011",
                "productName":"乐驾包4000", 
                "productShortName":"乐驾包5000", 
                "productPrice":350000,
                "remark":""
            },
            {
                "productId":"1012",
                "productName":"乐驾包2000", 
                "productShortName":"乐驾包5000", 
                "productPrice":150000,
                "remark":""
            }
        ]
        "request":"RESQUEST"
    }


参数|必须|说明
----|----|----
productId|是|产品编号
productName|是|产品名称
productShortName|是|产品简称，用于POS等终端展示，长度在6个汉字以内
productPrice|是|产品价格，单位：分
remark|是|备注
request|是|原请求内容，必须和原来请求一样

###线下开卡交割接口

中经汇通的POSP系统在约定的日切时间（该时间双方约定），将日切文件放到FTP服务器上。MAP前置在约定的日切时间+30分钟后，自动登录FTP服务器，并下载日切文件。根据日切文件和线下开卡订单记录，为未开通的卡开通，为拆开支付的订单进行合并，和进行开卡生效，对于无法合并的支付订单记录，交由人工处理。


1）访问文件说明

访问方式: ftp

    ftp://IP:PORT

  用户名和密码由中经汇通公司提供。


文件格式：

交易id|卡号|金额|流水号|参考号|商户号|终端号|批次号|交易日期|交易时间|身份证号|手机号|推荐人id|产品编号

2020030744015541000101023952001524000539|6225380083477614|0.01|000539|152412390951|307440155410001|01023952|001524|20141211|152412|123456789123456788|15017513750|1234


参数|必须|说明
----|----|----
交易id|是|支付系统的交易订单号（不用关注）
卡号|是|支付的卡号（不用关注）
金额|是|支付金额（元，精确到小数后2位）（关注）
流水号|是|即terminalOrderId
参考号|是|（不用关注）
商户号|是|即sellerId
终端号|是|即terminalId
批次号|是|即batchNo
交易日期|是|（关注）
交易时间|是|（关注）
身份证号|是|即ID
手机号|是|即mobileNum
推荐人id|是|即referrer(不关注)
产品编号|是|即productId

