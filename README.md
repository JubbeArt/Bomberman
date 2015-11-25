# Bomberman

## How to install: 
1. Skapa github konto
2. Installera git på din dator
3. Säg till mig att du har gjort det här så jag kan lägga dig i projektet


## How to download (clone) the project:
1. Öppna terminalen
2. Flytta dig, med hjälp av dir (directory, visar filer och mappar) och cd (change directory) till
din eclipse wordspace. Alltså ställ dig där alla mappar för dina projekt är.
3. Skriv: ```git``` (om jag minns rätt från Windows) för att få in i git-mode.
4. Hämta projektet genom att skriva: `git clone https://github.com/JubbeArt/Bomberman.git`

## How to fix with eclipse:
1. Gå till menyn `File -> Import`
2. `General -> Existing Project into Wordspace -> Next`
3. `Select root directory -> Browse -> Välj mappan Bomberman`
4. Done

## How to upload (push) updated code to git:
1. Ställ dig i eclipse wordspace. Gå in till Bomberman mappen.
2. Skriv: `git add map eller fil`, tex `git add src/` för att pusha upp alla java filer
3. Skriv: `git status` för att kolla så läget är okej
4. Skriv: `git commit -m "Bra medelande om dina ändringar, helst på engelska"`, tex `git add -m "Fixed collision in player class"`
5. Skriv: `git push origin master` för att pusha koden till github. 
Skriv dit användarnamn och lösenord för github

## How to get (pull) updated code from git:
1. Ställ dig i Bomberman mappen
2. Skriv: `git pull`
3. Skriv: `git status` för att kolla att allt är OK
