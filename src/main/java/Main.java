import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Livro;
import com.biblioteca.model.Membro;
import com.biblioteca.model.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Livro livro = new Livro(UUID.randomUUID(), "Livro Teste", Status.DISPONIVEL, "Autor Teste");
        Membro membro = new Membro(UUID.randomUUID(), "Membro Teste", "12345678900");

        LocalDate hoje = LocalDate.of(2026, 11, 12);
        LocalDate dataDevolucao = hoje.plusDays(14);

        LocalDate dataDevolvida = LocalDate.of(2026, 11, 24);

        Emprestimo emprestimo = new Emprestimo(UUID.randomUUID(), livro, membro, hoje, dataDevolucao);

        BigDecimal multaCalculada = emprestimo.calcularMulta(dataDevolvida);

        System.out.println(multaCalculada);
    }


}