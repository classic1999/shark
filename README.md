早些年使用shark(shark-1.1-2)做过工作流，后来shark在稳定性与性能方面达不到要求，我们自己写了一个工作流引擎，采用activiti的设计思想解析的xpdl文件，将所有的模型与流程实例都无缝的迁移了过去，这个就此荒废啦 
放在这个做一个备份啦，后续修改的可能性不超过0.01%,haha 是eclipse工程，数据库配置WebContent\conf
在shark1.1.2版本上改动点包括：

1.  添加获取xpdl接口
2.	修正shark中获取模型版本时的bug（用字符串比较数字的大小）
3.	添加死循环的判断，当某一个活动定义执行的次数超过一定数量时，认为流程发生了死循环 wfprocessImpl 807
4.	soap代理的修改，支持安全机制
5.	增加回退接口 gotowhere ,goback,goPrevious
6.	只支持同级别的操作（不能从块到流程的打回，跳转等）
7.	删除andjoin的计算
8.	修改shark中processId与activityId的生成策略 WfProcessMgrImpl WfProcessImpl
9.	添加通过processId查询流程定义的方法
10.	在新建流程时，packageversion由缓存取改为数据库查询，需要关闭dods的缓存
