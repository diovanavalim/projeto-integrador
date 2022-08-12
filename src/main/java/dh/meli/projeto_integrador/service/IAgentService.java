package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.model.Agent;

/**
 * Interface to specify service methods implemented on AgentService class.
 * @author Diovana Valim
 * @version 0.0.1
 */
public interface IAgentService {

    /**
     * Method implemented by AgentService for to find an Agent by Id
     * @param id long coming from user request
     * @return an object of type Agent
     */
    Agent findAgent(long id);
}
