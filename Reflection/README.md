# 打包、插件练习

## 项目结构

```
├─sort.jar
├─quicksort.jar
├─insertionsort.jar
├─sort.conf
├─manifest.mf
└─src
    └─edu
        ├─fudan
        │  └─ISort.java
        │  └─Sort.java
        ├─insertionsort
        │  └─InsertionSort.java
        └─quicksort
           └─QuickSort.java
```

## 打包

Windows 和 Linux 下的命令相同。打包*已经完成*。

```shell
jar cvfm sort.jar manifest.mf -C bin edu/fudan/ISort.class -C bin edu/fudan/Sort.class

jar cvf quicksort.jar -C bin edu/quicksort/QuickSort.class
jar cvf insertionsort.jar -C bin edu/insertionsort/InsertionSort.class
``` 

## 运行

需要在`sort.conf`中配置`plugin`为排序算法的实现类名（包含包名）。

需要`quicksort.jar`、`insertionsort.jar`和`sort.jar`在同一目录下。

```shell
java -jar sort.jar
```

## 插件

`quicksort.jar`和`insertionsort.jar`是插件，  
可以在`sort.conf`中配置排序算法的实现类名 plugin 为`edu.quicksort.QuickSort`或`edu.insertionsort.InsertionSort`。

也可以自己实现排序算法，打包成 jar 文件，放在同一目录下；并在`sort.conf`中配置plugin。

### config 格式
    
```properties
plugin = edu.quicksort.QuickSort
```

## 说明

不用`clazz.newInstance()`，因为`newInstance()`方法已经过时，用`clazz.getDeclaredConstructor().newInstance()`代替。

利用URLClassLoader加载 jar 文件，只需修改`sort.conf`，无须在`manifest.mf`中配置 Class-Path 并重新打包。
