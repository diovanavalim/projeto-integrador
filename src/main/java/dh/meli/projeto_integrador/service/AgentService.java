package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.exception.ResourceNotFoundException;
import dh.meli.projeto_integrador.model.Agent;
import dh.meli.projeto_integrador.repository.IAgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Class responsible for business rules and communication with the Agent Repository layer;
 * @author Diovana Valim;
 * @version 0.0.1
 */
@Service
public class AgentService implements IAgentService{

    /**
     * Dependency Injection of the Agent Repository.
     */
    @Autowired
    private IAgentRepository agentRepository;

    /**
     * Method to find an agent by id;
     * @param id of type long. Agent identifier;
     * @return an object of type Agent;
     */
    @Override
    public Agent findAgent(long id) {
        Optional<Agent> agent = agentRepository.findById(id);

        if (agent.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Could not find valid agent for id %d", id));
        }

        return agent.get();
    }
}
