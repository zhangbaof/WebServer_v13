###解决办法：

一、git fork不同步

git通过点击fork获取分组的项目时，发现自己的项目版本太低，需要回溯到分组的这个版本时，可以通过如下命令来进行同步：

1.输入git remote -v，按下回车键，你将会看到你的 fork 当前配置的远程仓库：
![](https://raw.githubusercontent.com/zhangbaof/WebServer_v13/master/20180628090536.png)
结果发现已经设置了上游，我想重新设置，最后在eclipse中路径下的remote下的上游zbf删除

![](https://raw.githubusercontent.com/zhangbaof/WebServer_v13/master/20180628093503.png)

再次输入git remote -v，如下图

![](https://raw.githubusercontent.com/zhangbaof/WebServer_v13/master/20180628093844.png)

2.输入git remote add zbf http://192.168.1.209:9091/zhangbf/ctf-java.git，添加之后再次键入git remote -v得到第一张图所示

3.同步远程仓库：
输入git pull upstream master，从远程分支拉取代码到本地  
![](https://raw.githubusercontent.com/zhangbaof/WebServer_v13/master/20180628094734.png)
 
push到自己的库里,git push zbf master,到了最后一步，结果报了错，报错如下：
![](https://raw.githubusercontent.com/zhangbaof/WebServer_v13/master/20180628095134.png)

最后输入git config --system --unset credential.helper
执行完毕之后再次输入git push zbf master时会需要输入你的密码,同步成功

![](https://raw.githubusercontent.com/zhangbaof/WebServer_v13/master/20180628100155.png)



[参照详解](https://blog.csdn.net/gent__chen/article/details/53305297)

二、git pull远程仓库报错：之前push远程仓库代码之后，修改代码，并未提交。

1.如果你想完全地覆盖本地的代码，只保留服务器端代码，则直接回退到上一个版本，再进行pull：

	执行
	(1)git reset --hard
	(2)git pull ① master

2.如果你想保留刚才本地修改的代码，并把git服务器上的代码pull到本地（本地刚才修改的代码将会被暂时封存起来）

	(1)git stash
	(2)git pull ① master
	(3)git stash pop

如此一来，服务器上的代码更新到了本地，而且你本地修改的代码也没有被覆盖，之后使用add，commit，push 命令即可更新本地代码到服务器了。

注：①指的是git remote -v命令执行后远程仓库的名称，也就是git主分支的名称




三、git push 出错

git push 出错如下：
![](https://raw.githubusercontent.com/zhangbaof/WebServer_v13/master/git%E4%B8%8A%E4%BC%A0/123465213.png)

解决方法：编辑项目下的.git文件夹中的config文件如下

![](https://raw.githubusercontent.com/zhangbaof/WebServer_v13/master/git%E4%B8%8A%E4%BC%A0/20180704123456.png)
修改完成之后重新push,输入密码即可


[git规范操作十条](https://github.com/zhangbaof/WebServer_v13/blob/master/git%E4%B8%8A%E4%BC%A0/git%E8%A7%84%E8%8C%83%E6%93%8D%E4%BD%9C%E5%8D%81%E6%9D%A1.md)