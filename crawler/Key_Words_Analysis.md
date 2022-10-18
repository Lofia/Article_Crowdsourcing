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
d=read.csv("crowd2Xu/Dictionary Maker/output/CrowdEpi.csv")
md=as.matrix(d[,2:dim(d)[2]])
head(md[,c(88,1:7)],20)
```

```
##       numWord validated survey worker natural patientslikeme quality text
##  [1,]    5006         0     50      0       0              0       0    0
##  [2,]    2675         1      0      0       0              0       0    0
##  [3,]    5756         0      6      0       1              0       0    0
##  [4,]    3934         0      0      0       0              0       0    0
##  [5,]    8963         1      0      0       0              0       2    0
##  [6,]    3147         1      1      0       0              0       0    0
##  [7,]    5553         5      5      0       0              0       0    0
##  [8,]    5759         0      2      0       0              0       0    0
##  [9,]    4776         1      0      0       0              0       0    0
## [10,]    4926         5     21      0       0              0       0    0
## [11,]    4382         2      5      0       0              0       0    0
## [12,]    3828         0      8      0       0              0       0    0
## [13,]    5076         0      1      0       0              0       0    0
## [14,]    8125         0      1      0       0              0       1    2
## [15,]    9638         2     10      0       0              0       0    3
## [16,]    4640         0     21      0       0              0       0    0
## [17,]    4496         1      5      0       0              0       0    0
## [18,]    4018         0      0      0       0              0       0    0
## [19,]   13258         0     23      0       0              0       0    0
## [20,]    4766         1      0      0       0              0       0    0
```

```r
d2=read.csv("crowd2Xu/Dictionary Maker/output/Bogus.csv")
md2=as.matrix(d2[,2:dim(d2)[2]])
head(md2[,c(88,1:7)],20)
```

```
##       numWord validated survey worker natural patientslikeme quality text
##  [1,]    2963         0      7      0       0              0       0    0
##  [2,]    4361         0      0      0       1              0       0    0
##  [3,]    8628         0      0      0       0              0       1    0
##  [4,]   11592         0      7      0       0              0       0    0
##  [5,]    6508         0      0      0       0              0       0    0
##  [6,]    4909         1      3      0       0              0       0    0
##  [7,]   14758         3      4      0       2              0       1    1
##  [8,]    5075         0      3      0       0              0       0    0
##  [9,]    4150         0      0      0       0              0       0    0
## [10,]    3703         0      0      0       0              0       0    0
## [11,]    3417         0      0      0       0              0       0    0
## [12,]    6769         0      1      0       0              0       0    0
## [13,]    7720         2      1      0       0              0       0    0
## [14,]    4569         0      1      0       0              0       0    0
## [15,]    4592         0      2      0       0              0       0    0
## [16,]   13335         0      1      0       1              0       0    0
## [17,]    2900         0     11      0       0              0       0    0
## [18,]    6405         0      0      0       0              0       0    0
## [19,]    8588         3      1      0       0              2       0    0
## [20,]   16659        14      4      0       0              0       0    0
```

### 2.Find the total occourance time

```r
s=colSums(md)
s2=colSums(md2)
show=cbind(t(t(s)),t(t(s2)))
colnames(show)=c('CrowdEpi','Bogus')
(WF=show[(show[,1]>0)&(show[,2]>0),])
```

```
##                  CrowdEpi   Bogus
## validated             310     116
## survey               2396     737
## worker                  7       5
## natural               103      29
## patientslikeme          1       6
## quality               114     102
## text                   55      22
## recruitment           550     116
## health               9036     339
## user                    4       3
## trained                21      23
## user.1                 18      11
## symptom               289      42
## crowd.sourcing         53     119
## participant            14       7
## validate              123     116
## data                   46      44
## performance          1388    1498
## microtask              15      29
## crowd.sourced         291     212
## non.expert             63      32
## crowd                  18       4
## diagnosis             695      50
## players                 8       4
## self.reported         331      87
## citizens                1       3
## individuals            19      13
## patient.reported       34       4
## crowdsourcing        4075    2008
## screen                207     157
## experience            739     362
## individual             22      17
## dictionary             49      20
## X23andme                2       4
## disease              2458     204
## respondents            53       6
## posts                   2       3
## virus                 419      30
## people                 13      10
## self.report           154      59
## extraction            302     160
## extraction.1          302     160
## online                 35       8
## posted.1                2       1
## online.1              155      24
## diseases              721      61
## medical              3015     403
## users                  16       7
## users.1                21      15
## participants          125      33
## annotate               54      78
## citizen               481     373
## crowdsource            63      30
## turk                   15       5
## image                  76      49
## reliability           389     311
## query                 134     154
## diagnose               49      10
## validation            668     322
## flu                   130       8
## annotated             171     151
## experienced           242     119
## amazon                281     135
## crowdsourced         1255     778
## workers                21      27
## gold                  179     111
## recruit               152      73
## train                  12      16
## post.1                  1       1
## turkers                 8       1
## extract               134     178
## numWord           2369845 1417653
```

### 3. Taking log odds ratio and draw the barplot
$$\text{Log Odds Ratio }=\log_2(\frac{\frac{\text{Number of occourance per term for CrowdEpi}}{\text{Number of total words for CrowdEpi}}}{\frac{\text{Number of occourance per term for Bogus}}{\text{Number of total words for Bogus}}})$$

```r
par(las=2,mar=c(5,7,4,1)+.1)
LOR=log2(WF[,1][-length(WF)/2]/WF[,2][-length(WF)/2]*WF[,2][length(WF)/2]/WF[,1][length(WF)/2])
barplot((sort(LOR,decreasing=FALSE)),horiz=TRUE,main="CrowdEpi v.s. Bogus")
```

![](Key_Words_Analysis_files/figure-html/unnamed-chunk-3-1.png)<!-- -->
