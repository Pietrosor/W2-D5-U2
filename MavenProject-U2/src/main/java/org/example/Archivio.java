public class Archivio {
    private list<ElementoCatalogo> catalogo;

 public Archivio(){
     this.catalogo = new ArrayList<>();
 }
}
public void aggiungiElemento(ElementoCatalogo elemento) throws ISBNDuplicatoException {
    if (catalogo.stream().anyMatch(e -> e.getIsbn().equals(elemento.getIsbn()))) {
        throw new ISBNDuplicatoException("Elemento con ISBN " + elemento.getIsbn() + " già presente!");
    }
    catalogo.add(elemento);
}
public ElementoCatalogo cercaPerISBN(String isbn) throws ElementoNonTrovatoException {
    return catalogo.stream()
            .filter(e -> e.getIsbn().equals(isbn))
            .findFirst()
            .orElseThrow(() -> new ElementoNonTrovatoException("Nessun elemento con ISBN " + isbn + " trovato!"));
}
public void rimuoviElemento(String isbn) {
    catalogo.removeIf(e -> e.getIsbn().equals(isbn));
}
public List<ElementoCatalogo> cercaPerAnno(int anno) {
    return catalogo.stream()
            .filter(e -> e.getAnnoPubblicazione() == anno)
            .collect(Collectors.toList());
}
public List<Libro> cercaPerAutore(String autore) {
    return catalogo.stream()
            .filter(e -> e instanceof Libro && ((Libro) e).getAutore().equalsIgnoreCase(autore))
            .map(e -> (Libro) e)
            .collect(Collectors.toList());
}
public void aggiornaElemento(String isbn, ElementoCatalogo nuovoElemento) throws ElementoNonTrovatoException {
    rimuoviElemento(isbn);
    aggiungiElemento(nuovoElemento);
}
public void mostraStatistiche() {
    int numeroLibri = catalogo.stream().filter(e -> e instanceof Libro).count();
    int numeroRiviste = catalogo.stream().filter(e -> e instanceof Rivista).count();
    ElementoCatalogo maxPagine = catalogo.stream().max(Comparator.comparingInt(ElementoCatalogo::getNumeroPagine)).orElse(null);
    double mediaPagine = catalogo.stream().mapToInt(ElementoCatalogo::getNumeroPagine).average().orElse(0);

    System.out.println("Numero totale di Libri: " + numeroLibri);
    System.out.println("Numero totale di Riviste: " + numeroRiviste);
    System.out.println("Elemento con più pagine: " + (maxPagine != null ? maxPagine.getTitolo() + " con " + maxPagine.getNumeroPagine() + " pagine" : "Nessun elemento"));
    System.out.println("Media delle pagine: " + mediaPagine);
}