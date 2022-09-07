#### A simple re-work based on the illustration in the article (with [code](learning_crawler_xml_etc.R)):
*"... We developed an extensible markup language (XML) crawler to retrieve targeted articles from Medline. It was guided by a rule-based configuration that identified articles by both their mesh term and the desired crawling depth. These rules performed an initial screening of the articles in order to best identify the appropriate documents for the final corpus. The crawler started the navigation by retrieving the MESH term “Crowdsourcing” using rentrez() from the XML library in R. This program harnesses NCBI's EUtils API for parsing databases such as GenBank and PubMed. Based on the matching mesh term, the crawler extracted the data based on the nested XML metadata from PubMed and Pubmed Central of PMID, Journal, Publication Date, Article Year, Article Title, and Author Name (First, Last). The extracted data were subsequently processed by plyr() from the R library and automatically compiled into a csv file. ..."*

---

[link to potential cs co-workers](https://gmuedu-my.sharepoint.com/:w:/g/personal/zxu27_gmu_edu/EaXsDAZUecdKpsOM8p5fVywBfUYG_nrvpDEFhynHqcWArA?e=8yTc4z)

---

Used [**BioC API**](https://www.ncbi.nlm.nih.gov/research/bionlp/APIs/BioC-PMC/) (with [code](pubmed_xml_crawler.R)) to extract selected .xml files from PubMed Central Open Access database.  
(By using the BioC API, the downloaded articles are from PMC Open Access Subset and the PMC Author Manuscript Collection. Not all articles in PMC are available for text mining and other reuse.)

---
<!-- ![alt text](https://github.com/Lofia/Article_Crowdsourcing/blob/main/crawler/Screenshot_medline.png) -->
Medline: A large dataset  
PubMed: A larger dataset containing Medline  
PubMed Central: A part (about 60%) of PubMed where all the articles in it are freely available  
PMC Open Access Subset & PMC Author Manuscript Collection: A part (about 80%) of PubMed Central where all the articles in it could be retrieved using certain API for text mining purpose    
![alt text](https://github.com/Lofia/Article_Crowdsourcing/blob/main/crawler/relation_graph.png)
Therefore:  
In PubMed, I searched for "crowdsourcing" in the recent five years (from 2017) with the restriction of "Medline" in PMC. And among them, 594 (about 80%) articles [(PMCOAS+AMC_medline(1031).zip)](PMCOAS+AMC_medline(594).zip) could be downloaded using BioC.  
In PubMed, I searched for "crowdsourcing" in the recent five years (from 2017) in PMC. And among them, 1031 (about 80%) articles [(PMCOAS+AMC_all(1031).zip)](PMCOAS+AMC_all(1031).zip) could be downloaded using BioC. ([link here](https://pubmed.ncbi.nlm.nih.gov/?term=crowdsourcing&filter=simsearch2.ffrft&filter=datesearch.y_5&sort=date&sort_order=asc))  
However, in PubMed Central, I searched for "crowdsourcing" in the recent five years (from 2017). And among them, 6025 (about 80%) articles could be downloaded using BioC. ([link here](https://www.ncbi.nlm.nih.gov/pmc/?term=crowdsourcing))
