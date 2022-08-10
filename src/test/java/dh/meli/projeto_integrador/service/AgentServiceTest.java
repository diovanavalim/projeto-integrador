package dh.meli.projeto_integrador.service;

import dh.meli.projeto_integrador.exception.ResourceNotFoundException;
import dh.meli.projeto_integrador.model.Agent;
import dh.meli.projeto_integrador.repository.IAgentRepository;
import dh.meli.projeto_integrador.util.Generators;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AgentServiceTest {

    @InjectMocks
    AgentService agentService;

    @Mock
    IAgentRepository agentRepository;

    @BeforeEach
    void setup() {
        BDDMockito.when(agentRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(Generators.getAgent()));
    }

    @Test
    void findAgentTest() {
        Agent agent = agentService.findAgent(0);

        assertThat(agent.getId()).isEqualTo(Generators.getAgent().getId());
        assertThat(agent.getName()).isEqualTo(Generators.getAgent().getName());
        assertThat(agent.getEmailAddress()).isEqualTo(Generators.getAgent().getEmailAddress());

        verify(agentRepository, atLeastOnce()).findById(agent.getId());
    }

    @Test
    void findAgent_WhenAgentDontExistsTest() throws Exception {
        BDDMockito.when(agentRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        long id = 0;

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            Agent agent = agentService.findAgent(id);
        });

        assertThat(exception.getMessage()).isEqualTo(String.format("Could not find valid agent for id %d", id));

        verify(agentRepository, atLeastOnce()).findById(id);
    }
}
