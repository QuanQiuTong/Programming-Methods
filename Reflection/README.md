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

用 package.bat (Windows) 或 package.sh (Linux) 打包。  
打包*已经完成*。

## 运行

需要在`sort.conf`中配置排序算法的实现类名 sortClass 以及排序算法的 jar 文件名 sortJar。

需要`quicksort.jar`、`insertionsort.jar`和`sort.jar`在同一目录下

```shell
java -jar sort.jar
```

## 插件

`quicksort.jar`和`insertionsort.jar`是插件，  
可以在`sort.conf`中配置排序算法的实现类名 className 为`edu.quicksort.QuickSort`或`edu.insertionsort.InsertionSort`。

也可以自己实现排序算法，打包成 jar 文件，放在同一目录下；  
并在`sort.conf`中配置排序算法的实现类名 sortClass 以及排序算法的 jar 文件名 sortJar。

### config 格式
    
```properties
sortJar = quicksort.jar
sortClass = edu.quicksort.QuickSort
```

## 说明

不用`clazz.newInstance()`，因为`newInstance()`方法已经过时，用`clazz.getDeclaredConstructor().newInstance()`代替。

利用URLClassLoader加载 jar 文件，只需修改`sort.conf`，无须在`manifest.mf`中配置 Class-Path 并重新打包。
