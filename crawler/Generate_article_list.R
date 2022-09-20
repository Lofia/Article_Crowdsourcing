# Generate_article_list.R

library(XML)
u=xmlToDataFrame('xx.xml')

# https://github.com/pixgarden/xsitemap
library(devtools)
library(xsitemap)
xsitemap_urls=xsitemapGet("https://www.nationalarchives.gov.uk/")
xsitemap_urls_http=xsitemapCheckHTTP(xsitemap_urls)


#7/17
library(RCurl)
library(XML)
url="https://scholar.google.com/scholar?hl=en&as_sdt=0%2C47&q=crowdsourcing+epidemiology&oq="
web=getURL(url)
doc=htmlTreeParse(web,encoding="UTF-8",error=function(...){},useInternalNodes=TRUE,trim=TRUE)


#real
library(RISmed)
search_query=EUtilsSummary('crowdsourcing',db='pubmed',retmax=10000,
                           mindate='2018/01/01',maxdate='2022/07/01')
summary(search_query)
#QueryId(search_query)
records=EUtilsGet(search_query)
output=data.frame(Year=YearAccepted(records),Month=MonthAccepted(records),
                  Date=DayAccepted(records),PMID=PMID(records),
                  Journal=ISOAbbreviation(records))#Title=ArticleTitle(records))
# YearAccepted(records)
# ArticleTitle(records)
# AbstractText(records)
# Mesh(records)
author=Author(records)
author=sapply(author,function(x){
  if(is.null(dim(x)[1]))
    return(NA)
  return(paste(unlist(x[1,])[3:2],collapse=" "))
  })
output$Author=author
output$Title=ArticleTitle(records)
setwd("C:/Users/Zixiang Xu/Desktop/GMU/JY Sun")
write.csv(output,'identified_articles_from_Medline.csv')
#write.table(output,'identified_articles_from_Medline.txt')
