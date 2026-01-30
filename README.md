Linkki YouTube-videoon: https://youtu.be/gvSGmnrUuTs
## Datamalli
"Task" data class:

- id = tehtävän tunniste 
- title = otsikko 
- description = kuvaus
- priority = tärkeys
- dueDate = määräpäivä 
- done/not done = tehtävän tila (valmis/kesken) 

## Funktiot
- addTask = Lisää uuden tehtävän
- toddleDone = Vaihtaa tehtävän tilan (valmis/kesken) 
- filterByDone = Valitsee tehtävistä ne, jotka ovat valmiita
- sortByDueDate = Järjestää tehtävät päivämäärän mukaan

## Compose-tilanhallinta:
Jetpack Composessa UI reagoi tilaan. Kun tila muuttuu, Compose piirtää vain tarvittavat osat uudelleen automaattisesti.

## Miksi ViewModel on parempi kuin remember:
**remember** säilyttää tilan vain niin kauan kuin näkymä on muistissa, ja se katoaa esimerkiksi näytön käännössä.
**ViewModel** säilyttää tilan pidempään, selviää konfiguraatiomuutoksista ja pitää sovelluksen logiikan erillään käyttöliittymästä.

**MVVM**: Model-View-ViewModel on arkkitehtuurimalli, jossa Model sisältää datan ja logiikan, View näyttää käyttöliittymän ja ViewModel hallitsee tilaa ja välittää datan View:lle.

Se on hyödyllinen Compose-sovelluksissa, koska ViewModel säilyy konfiguraatiomuutoksissa, erottaa UI-logiikan datasta ja mahdollistaa automaattiset päivitykset Compose-komponenteissa.

**StateFlow** on Kotlinin Flow, joka pitää yllä viimeisintä arvoa ja lähettää muutokset automaattisesti kuuntelijoille. UI voi kerätä StateFlow’n ja päivittyä automaattisesti, kun tila muuttuu.
