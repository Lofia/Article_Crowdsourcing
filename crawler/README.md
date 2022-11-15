
# Table of Contents  ### [*generated with markdown-toc*](http://ecotrust-canada.github.io/markdown-toc/)

- [7-13-2022](#7-13-2022)
- [7-20-2022](#7-20-2022)
- [8-24-2022](#8-24-2022)
- [8-31-2022](#8-31-2022)
- [9-7-2022](#9-7-2022)
- [9-14-2022](#9-14-2022)
- [9-21-2022](#9-21-2022)
- [9-28-2022](#9-28-2022)
- [10-5-2022](#10-5-2022)
- [10-12-2022](#10-12-2022)
- [10-19-2022](#10-19-2022)
- [10-26-2022](#10-26-2022)
- [11-2-2022](#11-2-2022)
- [11-9-2022](#11-9-2022)
- [11-16-2022](#11-16-2022)

---
### 7-13-2022
Wrote the code [**Generate_article_list.R**](Generate_article_list.R) to generate the list of ids of our targeted articles, which is a re-work based on the illustration in the unfinished paper:  
*"... We developed an extensible markup language (XML) crawler to retrieve targeted articles from Medline. It was guided by a rule-based configuration that identified articles by both their mesh term and the desired crawling depth. These rules performed an initial screening of the articles in order to best identify the appropriate documents for the final corpus. The crawler started the navigation by retrieving the MESH term “Crowdsourcing” using rentrez() from the XML library in R. This program harnesses NCBI's EUtils API for parsing databases such as GenBank and PubMed. Based on the matching mesh term, the crawler extracted the data based on the nested XML metadata from PubMed and Pubmed Central of PMID, Journal, Publication Date, Article Year, Article Title, and Author Name (First, Last). The extracted data were subsequently processed by plyr() from the R library and automatically compiled into a csv file. ..."*

---
### 7-20-2022
Prepared a [link to potential cs co-workers](https://gmuedu-my.sharepoint.com/:w:/g/personal/zxu27_gmu_edu/EaXsDAZUecdKpsOM8p5fVywBfUYG_nrvpDEFhynHqcWArA?e=8yTc4z).

---
### 8-24-2022
Wrote the code [**Fetch_xml_files.R**](Fetch_xml_files.R), using the [BioC API](https://www.ncbi.nlm.nih.gov/research/bionlp/APIs/BioC-PMC/), to extract selected .xml files from PubMed Central Open Access database.  
(By using the BioC API, the downloaded articles are from PMC Open Access Subset and the PMC Author Manuscript Collection. Not all articles in PMC are available for text mining and other reuse.)

---
### 8-31-2022
Found another way to get the list of targeted articles simply by searching through the website of PubMed:
<div align="center"><img src="https://github.com/Lofia/Article_Crowdsourcing/blob/main/crawler/Screenshot_medline.png" width="700"></div>

---
### 9-7-2022
**Medline**: A large dataset  
**PubMed**: A larger dataset containing Medline  
**PMC**(PubMed Central): A part (about 60%) of PubMed where all the articles in it are freely available  
**PMC OAS**(Open Access Subset) & **PMC AMC**(Author Manuscript Collection): A part (about 80%) of PubMed Central where all the articles in it could be retrieved using certain API for text mining purpose
<div align="center"><img src="https://github.com/Lofia/Article_Crowdsourcing/blob/main/crawler/relation_graph.png" width="800"></div>

As the above graph shows, the real available dataset for us is the yellow circled one (PMC OAS & AMC), where "Medline" is still the basic part. Therefore:  
In PubMed, I searched for "crowdsourcing" in the recent five years (from 2017) within "Medline". And among them, 594 (about 80%) articles [(PMCOAS+AMC_Medline(594).zip)](PMCOAS+AMC_Medline(594).zip) could be downloaded using the code [Fetch_xml_files.R](Fetch_xml_files.R).  
In PubMed, I searched for "crowdsourcing" in the recent five years (from 2017). And among them, 1031 (about 80%) articles [(PMCOAS+AMC_all(1031).zip)](PMCOAS+AMC_all(1031).zip) could be downloaded using the code [Fetch_xml_files.R](Fetch_xml_files.R). ([link here](https://pubmed.ncbi.nlm.nih.gov/?term=crowdsourcing&filter=simsearch2.ffrft&filter=datesearch.y_5&sort=date&sort_order=asc))  
*However, in PubMed Central, I searched for "crowdsourcing" in the recent five years (from 2017). And among them, 6025 (about 80%) articles could be downloaded using the code [Fetch_xml_files.R](Fetch_xml_files.R). ([link here](https://www.ncbi.nlm.nih.gov/pmc/?term=crowdsourcing))*

---
### 9-14-2022
Converted all the .xml files to .txt files straightly by changing their extensions. And we are able to run the "Dictionary Maker" java code (Gui.java) on all the converted text files. For example [New202009.csv](New202009.csv).  
Though there presents a samll problem that, since we are simply changing extensions, the converted .txt files are not very clean. For example:  
<div align="center"><img src="https://github.com/Lofia/Article_Crowdsourcing/blob/main/crawler/Screenshot_java_prob.png"></div>
the word "Given" could not be detected since there is no space separating it at beginning.

---
### 9-21-2022
Solved the problem of last time by writing the code [**convert_xml2txt.R**](convert_xml2txt.R). That is, we can now convert xml files to clean text documents without structure nodes. For example [28058518.txt](PMCOAS+AMC_Medline_txt/28058518.txt).

---
### 9-28-2022
Read the article [Tensors in Modern Statistical Learning](https://scholar.google.com/scholar_url?url=https://web.ics.purdue.edu/~sun244/Tensor_survey.pdf&hl=en&sa=X&ei=7mc7Y4nPGKLGsQKWx5S4BA&scisig=AAGBfm0mejFf1szdALN-5Bq548yYMIX4SQ&oi=scholarr).

---
### 10-5-2022
More about [the relation of tensor mode-d prodect and Kronecker product](https://math.stackexchange.com/questions/1956155/n-mode-product-and-kronecker-product-relation).  
Key idea: In the model of 
$$\mathscr{Y} = \mathscr{X} \times_1 A^{(1)} \times_2 A^{(2)} \ldots \times_N A^{(N)} \iff Y_{(n)} = A^{(n)}X_{(n)}(A^{(N)}\otimes \ldots \otimes A^{(n+1)} \otimes A^{(n-1)} \otimes \ldots \otimes A^{(1)})^T$$
the column-index of an element in the matricized tensor $\mathscr{X}_{(n)}$ is the same as the row-index of the corresponding Kronecker products $(A^{(N)}\otimes \ldots \otimes A^{(n+1)} \otimes A^{(n-1)} \otimes \ldots \otimes A^{(1)})^T$.

---
### 10-12-2022
Wrote R Markdown code to do simple analysis for generated dictionary: [View it here](Key_Words_Analysis.md).

---
### 10-19-2022
Updated the analysis code: [View it here](Key_Words_Analysis.md).

---
### 10-26-2022
Skipped (Meeting together with Dr. Ziyu Yao)

---
### 11-2-2022
Planned to re-analyze the old data to check our present process.

---
### 11-9-2022
Updated the analysis code by combining similar words, and integrating with old data: [View it here](Key_Words_Analysis.md).

---
### 11-16-2022
Analyzed the possible differences between the 188 peer-reviewed articles and the 68/63 articles used to generate the keywords: [View it here](188_68_63.md).  
In page 6, the paper [crowdsourcepaper_v12_KC_edits](crowdsourcepaper_v12_KC_edits.pdf) mentioned that  
*"...For our training dataset, one-third (N=63) of the articles were selected from the final corpus to be manually annotated by 3 independent raters..."*  
However, there seems to be only about half of the 68/63 articles are from the 188 peer-reviewed articles.  
And one possible reason of the 5 deleted articles (68\63) is that those five are neither epidemiology nor crowdsourcing.
