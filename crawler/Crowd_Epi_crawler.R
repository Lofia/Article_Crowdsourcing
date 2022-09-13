library(easyPubMed)
library(devtools)
install_github("dami82/easyPubMed")

my_query <- 'Damiano Fantini[MESH]'
my_entrez_id <- get_pubmed_ids(my_query)
my_abstracts_txt <- fetch_pubmed_data(my_entrez_id, format = "abstract")

setwd("C:/Users/vselabs/Desktop")
r=read.csv("identified_articles_from_Medline.csv")
head(r)
id=r$PMID
retrive=fetch_pubmed_data(pubmed_id_list = id)


library(RCurl)
library(XML)
myurl="https://www.ncbi.nlm.nih.gov/research/bionlp/RESTful/pmcoa.cgi/BioC_xml/31571341/unicode"
web=getURL(myurl)
doc=htmlTreeParse(web,error=function(...){},useInternalNodes=TRUE,trim=TRUE)


# real
# BioC API for PMC
library(xml2)
setwd("C:/Users/vselabs/Desktop")
r=read.csv("identified_articles_from_Medline.csv")
id=r$PMID
XMLdownload=function(x){
  tryCatch({
  myurl=paste('https://www.ncbi.nlm.nih.gov/research/bionlp/RESTful/pmcoa.cgi/BioC_xml/',x,'/unicode',sep='')
  download_xml(myurl,paste('pubmed/',x,'.xml',sep=''),quiet=FALSE)
  },error=function(e){cat("ERROR :",conditionMessage(e), "\n")})
}
sapply(id,XMLdownload)


# Medline
library(xml2)
setwd("C:/Users/Zixiang Xu/Desktop/GMU/JY Sun")
id=read.table('pmid-crowdsourc-set.txt')
pmc_medline_id=as.vector(id)$V1
id=read.table('pmid-crowdsourc-set2.txt')
pmc_pubmed_id=as.vector(id)$V1
id=read.csv("identified_articles_from_Medline.csv")$PMID

XMLdownload2=function(x){
  tryCatch({
    myurl=paste('https://www.ncbi.nlm.nih.gov/research/bionlp/RESTful/pmcoa.cgi/BioC_xml/',x,'/unicode',sep='')
    download_xml(myurl,paste('PMCOAS_Medline/',x,'.xml',sep=''),quiet=FALSE)
  },error=function(e){cat("ERROR :",conditionMessage(e), "\n")})
}
sapply(pmc_medline_id,XMLdownload2)

XMLdownload3=function(x){
  tryCatch({
    myurl=paste('https://www.ncbi.nlm.nih.gov/research/bionlp/RESTful/pmcoa.cgi/BioC_xml/',x,'/unicode',sep='')
    download_xml(myurl,paste('PMCOAS_all/',x,'.xml',sep=''),quiet=FALSE)
  },error=function(e){cat("ERROR :",conditionMessage(e), "\n")})
}
sapply(pmc_pubmed_id,XMLdownload3)

