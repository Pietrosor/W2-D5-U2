package org.example;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Archivio archivio = new Archivio();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n MENU - Catalogo Bibliotecario");
            System.out.println("1) Aggiungi elemento");
            System.out.println("2) Cerca per ISBN");
            System.out.println("3) Rimuovi elemento");
            System.out.println("4) Cerca per anno di pubblicazione");
            System.out.println("5) Cerca per autore");
            System.out.println("6) Aggiorna elemento");
            System.out.println("7) Mostra statistiche");
            System.out.println("8) Esci");
            System.out.print("Scegli un'opzione: ");

            int scelta;
            try {
                scelta = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido!");
                continue;
            }

            switch (scelta) {
                case 1:
                    aggiungiElemento(archivio, scanner);
                    break;
                case 2:
                    cercaPerISBN(archivio, scanner);
                    break;
                case 3:
                    rimuoviElemento(archivio, scanner);
                    break;
                case 4:
                    cercaPerAnno(archivio, scanner);
                    break;
                case 5:
                    cercaPerAutore(archivio, scanner);
                    break;
                case 6:
                    aggiornaElemento(archivio, scanner);
                    break;
                case 7:
                    archivio.mostraStatistiche();
                    break;
                case 8:
                    System.out.println("Programma terminato.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Scelta non valida, riprova!");
            }
        }
    }

    private static void aggiungiElemento(Archivio archivio, Scanner scanner) {
        System.out.print("Vuoi aggiungere un libro o una rivista? (L/R): ");
        String tipo = scanner.nextLine().trim().toUpperCase();

        System.out.print("Inserisci ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Titolo: ");
        String titolo = scanner.nextLine();
        System.out.print("Anno di pubblicazione: ");
        int annoPubblicazione = Integer.parseInt(scanner.nextLine());
        System.out.print("Numero pagine: ");
        int numeroPagine = Integer.parseInt(scanner.nextLine());

        try {
            if (tipo.equals("L")) {
                System.out.print("Autore: ");
                String autore = scanner.nextLine();
                System.out.print("Genere: ");
                String genere = scanner.nextLine();
                archivio.aggiungiElemento(new Libro(isbn, titolo, annoPubblicazione, numeroPagine, autore, genere));
                System.out.println("Libro aggiunto!");
            } else if (tipo.equals("R")) {
                System.out.print("Periodicità (SETTIMANALE, MENSILE, SEMESTRALE): ");
                Rivista.Periodicita periodicita = Rivista.Periodicita.valueOf(scanner.nextLine().toUpperCase());
                archivio.aggiungiElemento(new Rivista(isbn, titolo, annoPubblicazione, numeroPagine, periodicita));
                System.out.println("Rivista aggiunta!");
            } else {
                System.out.println("Scelta non valida!");
            }
        } catch (IllegalArgumentException | ISBNDuplicatoException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    private static void cercaPerISBN(Archivio archivio, Scanner scanner) {
        System.out.print("Inserisci ISBN da cercare: ");
        String isbn = scanner.nextLine();
        try {
            ElementoCatalogo elemento = archivio.cercaPerISBN(isbn);
            System.out.println("Elemento trovato: " + elemento);
        } catch (ElementoNonTrovatoException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void rimuoviElemento(Archivio archivio, Scanner scanner) {
        System.out.print("🗑️ Inserisci ISBN dell'elemento da rimuovere: ");
        String isbn = scanner.nextLine();
        archivio.rimuoviElemento(isbn);
        System.out.println("✅ Elemento rimosso!");
    }

    private static void cercaPerAnno(Archivio archivio, Scanner scanner) {
        System.out.print("Inserisci anno di pubblicazione: ");
        int anno = Integer.parseInt(scanner.nextLine());
        List<ElementoCatalogo> risultati = archivio.cercaPerAnno(anno);
        if (!risultati.isEmpty()) {
            System.out.println("Elementi trovati:");
            risultati.forEach(System.out::println);
        } else {
            System.out.println("Nessun elemento trovato per l'anno " + anno);
        }
    }

    private static void cercaPerAutore(Archivio archivio, Scanner scanner) {
        System.out.print("Inserisci autore: ");
        String autore = scanner.nextLine();
        List<Libro> risultati = archivio.cercaPerAutore(autore);
        if (!risultati.isEmpty()) {
            System.out.println("Libri trovati:");
            risultati.forEach(System.out::println);
        } else {
            System.out.println("Nessun libro trovato per l'autore " + autore);
        }
    }

    private static void aggiornaElemento(Archivio archivio, Scanner scanner) {
        System.out.print("Inserisci ISBN dell'elemento da aggiornare: ");
        String isbn = scanner.nextLine();

        try {
            ElementoCatalogo vecchioElemento = archivio.cercaPerISBN(isbn);
            System.out.println("Elemento esistente: " + vecchioElemento);
            System.out.println("Ora inserisci i nuovi dati.");
            aggiungiElemento(archivio, scanner);
            System.out.println("Elemento aggiornato!");
        } catch (ElementoNonTrovatoException e) {
            System.out.println(e.getMessage());
        }
    }
}