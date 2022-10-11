---
title: "Keywords Analysis"
author: "Zixiang Xu"
output:
  html_document:
    keep_md: true
    number_sections: no
    theme: united
    toc: yes
    toc_float: yes
  html_notebook:
    theme: united
    toc: no
    toc_float: yes
  word_document:
    toc: no
  pdf_document:
    toc: no
---

```{=html}
<style type="text/css">
    #header {
        text-align: center;
    }
</style>
```


### 1.Read the dictionary

```r
d=read.csv("crowd2Xu/Dictionary Maker/output/Crowdsourcing Updated.csv")
md=as.matrix(d[,2:dim(d)[2]])
head(md[,c(88,1:7)],20)
```

```
##       numWord validated survey worker natural patientslikeme quality text
##  [1,]    7330         0     11      0       0              0       0    0
##  [2,]    5006         0     50      0       0              0       0    0
##  [3,]    2963         0      7      0       0              0       0    0
##  [4,]    2675         1      0      0       0              0       0    0
##  [5,]    5111         1      1      0       0              0       0    0
##  [6,]    4361         0      0      0       1              0       0    0
##  [7,]    7291         0      0      0       0              0       0    0
##  [8,]    5959         0      1      0       0              0       0    0
##  [9,]    8628         0      0      0       0              0       1    0
## [10,]    5832         0      1      0       0              0       0    0
## [11,]   11592         0      7      0       0              0       0    0
## [12,]    5756         0      6      0       1              0       0    0
## [13,]    3934         0      0      0       0              0       0    0
## [14,]    5926         0     20      0       0              0       0    0
## [15,]    6934         0      1      0       0              0       0    0
## [16,]    4987         0     11      0       0              0       0    0
## [17,]    5061         1     10      0       0              0       0    0
## [18,]    8963         1      0      0       0              0       2    0
## [19,]    3147         1      1      0       0              0       0    0
## [20,]    2854         1      0      0       0              0       0    0
```

### 2.Find the total occourance time

```r
s=colSums(md)
t(t(s))
```

```
##                     [,1]
## validated            414
## survey              3038
## worker                12
## natural              131
## patientslikeme         7
## quality              210
## text                  77
## recruitment          681
## health              9522
## user                   7
## patient                0
## trained               42
## user.1                26
## symptom              330
## crowd.sourcing       172
## participant           21
## validate             238
## data                  87
## performance         2790
## microtask             36
## crowd.sourced        498
## non.expert            97
## player                 2
## crowd                 22
## knowledge              2
## diagnosis            746
## players               12
## self.reported        421
## patient.report         0
## citizens               4
## individuals           30
## patient.reported      40
## crowdsourcing       6104
## screen               380
## self                   1
## experience          1074
## extracted              3
## individual            39
## dictionary            69
## X23andme               6
## disease             2651
## respondents           59
## posts                  5
## posts.1                1
## virus                447
## people                24
## self.report          215
## extraction           454
## extraction.1         454
## posted                 3
## online                42
## posted.1               3
## online.1             176
## crowd.source           9
## diseases             779
## medical             3430
## users                 23
## users.1               36
## participants         153
## annotate             130
## posting                0
## posting.1              1
## citizen              850
## crowdsource           90
## turk                  20
## image                123
## reliability          726
## query                269
## diagnose              59
## validation           956
## combine                0
## flu                  138
## annotated            313
## experienced          354
## extracts               0
## amazon               412
## crowdsourced        2039
## workers               47
## gold                 290
## recruit              229
## train                 25
## extracting             0
## post                   4
## post.1                 3
## turkers                9
## extract              307
## extract.1              0
## numWord          3741120
```

### 3.Barplot

```r
par(las=2,mar=c(5,7,4,1)+.1)
barplot((sort(s[-length(s)],decreasing=FALSE)[1:50]),horiz=TRUE,main="Keywords frequencies")
```

![](Key_Words_Analysis_files/figure-html/unnamed-chunk-3-1.png)<!-- -->

### 4.Bogus?

