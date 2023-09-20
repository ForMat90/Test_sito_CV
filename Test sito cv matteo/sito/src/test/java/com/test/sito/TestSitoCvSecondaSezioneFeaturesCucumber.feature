Feature: TestSitoCvSecondaSezioneCucumber
  Come utente del sito, voglio cliccare sulla sezione "I miei lavori", controllare cosa vedo, cliccare sul pulsante "Guarda la galleria completa"
  e vedere se aumentano le sezioni cliccabili nella pagina definita e voglio provare ad aprire un progetto per vedere se mi rimanda su GitHub

  As a website user, I want to click on the 'My Works' section, check what I see, click on the 'View Full Gallery' button,
  and see if there are more clickable sections on the designated page. I also want to try opening a project to see if it redirects me to GitHub.

  Scenario:
    Given I want to click on the 'My Works' section
    When I see correct section, i want to click 'View Full Gallery' button
    Then See if there are more clickable sections on the designated page in 'Other Works'
    When I try opening project
    Then I redirects to GitHub
    And I want to close new page