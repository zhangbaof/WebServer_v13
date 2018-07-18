Git使用 

----------

1. 登录gitlab，找到项目
![](https://i.imgur.com/dxve6Rp.png)
1. 进入项目，将项目fork到自己的仓库
![](https://i.imgur.com/oVIChoE.png)
1. 以上完成之后可看见在自己的仓库出现该项目
![](https://i.imgur.com/VEjHhp5.png)
1. 进入本地电脑，你想把项目放入的目录，打开git
![](https://i.imgur.com/jAgKo2I.png)
1. 执行命令
 `git clone http://192.168.1.209:9091/lihongsheng/cepo-devops.git`
   出现4中的cepo-devops目录
![](https://i.imgur.com/9I9wuIS.png)
1. 添加远程上联目录，执行命令
 `git remote add up http://192.168.1.209:9091/tangfeixiong/cepo-devops.git`
  即可 `git remove -v` 可查看本地项目与远程主分支
![](https://i.imgur.com/ekbJPcO.png)
1. 在本地项目内添加想要上传项目的文档或者代码等内容（目录最好不要建立空的，git会检查并忽略空目录）
![](https://i.imgur.com/dOV3ysB.png)

1. 上传到自己的仓库

![](https://i.imgur.com/02QmcKT.png)

![](https://i.imgur.com/8GrMA9D.png)

![](https://i.imgur.com/6Gs5E0P.png)


9.请求上级合并。进入到项目
![](https://i.imgur.com/S2mLazw.png)

![](https://i.imgur.com/Fcv3Uw1.png)

![](https://i.imgur.com/EUZYREy.png)

10.pull最新的项目，命令git pull up master

![](https://i.imgur.com/9ZDNKBn.png)