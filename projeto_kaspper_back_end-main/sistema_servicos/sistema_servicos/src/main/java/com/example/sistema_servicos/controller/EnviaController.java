package com.example.sistema_servicos.controller;

import com.example.sistema_servicos.model.EmailService;
import com.example.sistema_servicos.model.Servico;
import com.example.sistema_servicos.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class EnviaController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ServicoRepository servicoRepository;

    @GetMapping("/enviar-servico-email")
    public String enviarServicoPorEmail(@RequestParam("id") Long servicoId, @RequestParam("to") String para) {
        try {
            // Busca o serviço pelo ID
            Optional<Servico> optionalServico = servicoRepository.findById(servicoId);
            if (optionalServico.isPresent()) {
                Servico servico = optionalServico.get();

                // Monta o conteúdo do e-mail com os dados do serviço
                String assunto = "Detalhes do Serviço da Kaspper: " + servico.getDescricao();
                String conteudo = """
                        Descrição: %s
                        Linguagem: %s
                        Valor Hora: R$ %.2f
                        Dias Trabalhados: %d
                        Horas por Dia: %d
                        Valor Total do Projeto: R$ %.2f
                        """.formatted(
                        servico.getDescricao(),
                        servico.getLinguagem(),
                        servico.getValorHora(),
                        servico.getDiaTrabalhado(),
                        servico.getHoraDia(),
                        servico.getValorTotalProjeto()
                );

                // Envia o e-mail
                emailService.enviarEmail(para, assunto, conteudo);
                return "redirect:/servicos/lista?emailSucesso=true"; // Sucesso no envio
            } else {
                return "redirect:/servicos/lista?erro=ServicoNaoEncontrado"; // Caso o serviço não exista
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/servicos/lista?emailErro=true"; // Erro ao enviar o e-mail
        }
    }
}
