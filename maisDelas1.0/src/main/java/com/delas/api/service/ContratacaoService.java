package com.delas.api.service;
import com.delas.api.model.ContratacaoModel;
import com.delas.api.repository.ContratacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ContratacaoService {
    @Autowired private ContratacaoRepository contratacaoRepository;



        // Método para salvar um novo serviço
        public ContratacaoModel save(ContratacaoModel servico) {
            return contratacaoRepository.save(servico);
        }

        // Método para listar todos os serviços
        public List<ContratacaoModel> findAll() {
            return contratacaoRepository.findAll();
        }

        // Método para encontrar um serviço pelo ID
        public Optional<ContratacaoModel> findById(Long id) {
            return contratacaoRepository.findById(id);
        }


        // Método para deletar um serviço pelo ID
        public boolean deleteById(Long id) {
            if (contratacaoRepository.existsById(id)) {
                contratacaoRepository.deleteById(id);
                return true;
            }
            return false;
        }
    }


