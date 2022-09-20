# convert_xml2txt.R

library(xml2)
setwd("C:/Users/Zixiang Xu/Desktop/GMU/JY Sun")
folder='PMCOAS+AMC_all'
files=list.files(path=paste(folder,'/',sep=''),pattern='*.xml',full.names=FALSE)
files=gsub("\\..*","",files)
dir.create(paste(folder,'_txt',sep=''),showWarnings=FALSE)
f=function(x){
  pg=read_xml(paste(folder,'/',x,'.xml',sep=''))
  recs=xml_find_all(pg,'//text')
  vals=trimws(xml_text(recs))
  write.table(vals,paste(folder,'_txt','/',x,'.txt',sep=''),quote=FALSE,row.names=FALSE,col.names=FALSE)
  print(x)
}
sapply(files,f)



# trash

pg=read_xml("PMCOAS+AMC_Medline_Copy/28058518.xml")
recs=xml_find_all(pg,'//text')
vals=trimws(xml_text(recs))


strsplit('28058518.xml',split = ".xml")[[1]]

root=xml_children(pg)
cn=xml_child(pg,search=passage)


a=xml_find_all(pg,'//infon')
b=trimws(xml_text(a))
xml_attr(pg,'key')


library(XML)
doc<-xmlParse("PMCOAS+AMC_Medline_Copy/28058518.xml")
xmlToList(doc)
xmlTextNode(doc)


pg <- read_xml("http://www.ggobi.org/book/data/olive.xml")
recs <- xml_find_all(pg, "//record")
vals <- trimws(xml_text(recs))
labs <- trimws(xml_attr(recs, "label"))
