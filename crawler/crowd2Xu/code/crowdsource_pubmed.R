get_pubmed <- function(f){
    library(rentrez)
    library(XML)
    library(plyr)
    # Search Pubmed for mesh terms
    es <- entrez_search("pubmed", "\"Crowdsourcing\"[MESH]", usehistory = "y")
    # Pull out XML data
    xml <- entrez_fetch("pubmed", WebEnv = es$WebEnv, query_key=es$QueryKey,
                    rettype = "xml", retmax = es$count)
    g <- llply(xml, .progress = progress_tk(label="Fetching and parsing Pubmed records..."), function(x){
    xmlTreeParse(x, useInternalNodes = TRUE)
})
    k <- ldply(g, .progress=progress_tk("Creating dataframe..."), function(x){
        a<-getNodeSet(x, "/PubmedArticleSet/*/MedlineCitation")
        pmid_l<-sapply (a, function(a) xpathSApply(a, "./PMID", xmlValue))
        pmid<-lapply(pmid_l, function(x) ifelse(class(x)=="list" | class(x)=="NULL", NA, x))
        year_l<-sapply (a, function(a) xpathSApply(a, "./Article/Journal/JournalIssue/PubDate/Year", xmlValue))
        year<-lapply(year_l, function(x) ifelse(class(x)=="list" | class(x)=="NULL", NA, x))
        journal_l<-sapply (a, function(a) xpathSApply(a, "./Article/Journal/Title", xmlValue))
        journal<-lapply(journal_l, function(x) ifelse(class(x)=="list" | class(x)=="NULL", NA, x))
        title_l<-sapply (a, function(a) xpathSApply(a, "./Article/ArticleTitle", xmlValue))
        title<-lapply(title_l, function(x) ifelse(class(x)=="list" | class(x)=="NULL", NA, x))
        author_l<-sapply (a, function(a) xpathSApply(a, "./Article/AuthorList/Author[1]/LastName", xmlValue))
        author<-lapply(author_l, function(x) ifelse(class(x)=="list" | class(x)=="NULL", NA, x))
        cwd.df <- data.frame(pmid=unlist(pmid), year=unlist(year), journal=unlist(journal), title=unlist(title), author=unlist(author))
        write.csv(cwd.df, "crowdsourcedarticles.csv") 
    })
}
