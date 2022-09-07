#### A simple re-work based on the illustration in the article (with [code](learning_crawler_xml_etc.R)):
*"... We developed an extensible markup language (XML) crawler to retrieve targeted articles from Medline. It was guided by a rule-based configuration that identified articles by both their mesh term and the desired crawling depth. These rules performed an initial screening of the articles in order to best identify the appropriate documents for the final corpus. The crawler started the navigation by retrieving the MESH term “Crowdsourcing” using rentrez() from the XML library in R. This program harnesses NCBI's EUtils API for parsing databases such as GenBank and PubMed. Based on the matching mesh term, the crawler extracted the data based on the nested XML metadata from PubMed and Pubmed Central of PMID, Journal, Publication Date, Article Year, Article Title, and Author Name (First, Last). The extracted data were subsequently processed by plyr() from the R library and automatically compiled into a csv file. ..."*

---

[link to potential cs co-workers](https://gmuedu-my.sharepoint.com/:w:/g/personal/zxu27_gmu_edu/EaXsDAZUecdKpsOM8p5fVywBfUYG_nrvpDEFhynHqcWArA?e=8yTc4z)

---

Used [**BioC API**](https://www.ncbi.nlm.nih.gov/research/bionlp/APIs/BioC-PMC/) (with [code](pubmed_xml_crawler.R)) to extract selected .xml files from PubMed Central Open Access database.

---
<!-- ![alt text](https://github.com/Lofia/Article_Crowdsourcing/blob/main/crawler/Screenshot_medline.png) -->

The downloaded articles are from PMC Open Access Subset and the PMC Author Manuscript Collection. Not all articles in PMC are available for text mining and other reuse.
