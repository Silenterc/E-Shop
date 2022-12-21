package cz.cvut.fit.zimaluk1.tjv.tjveshop.controllerTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.api.CustomerController;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.api.dto.CustomerDto;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.business.CustomerService;
import cz.cvut.fit.zimaluk1.tjv.tjveshop.domain.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CustomerController.class)
public class CustomerControllerAndCRUDTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService service;
    @Autowired
    ModelMapper map;

    private ObjectMapper mapper = new ObjectMapper();
    @Test
    public void createTest() throws Exception{
        Customer newOne = new Customer(2L, "Lukas", "lukas@test.cz", "Praha", 100L);
        when(service.create(any())).thenReturn(newOne);
        CustomerDto newOneDto = new CustomerDto(2L, "Lukas", "lukas@test.cz", "Praha", 100L);
        String request = mapper.writeValueAsString(newOneDto);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                .contentType(MediaType.APPLICATION_JSON).content(request))
                        .andExpect(status().isOk()).andReturn();
        Mockito.verify(service, times(1)).create(any());
        Assertions.assertEquals(request, result.getResponse().getContentAsString());
    }

    @Test
    public void updateTest() throws Exception {
        //Successful update
        Customer newOne = new Customer(2L, "Lukas", "lukas@test.cz", "Praha", 100L);
        CustomerDto newOneDto = new CustomerDto(2L, "Lukas", "lukas@test.cz", "Praha", 100L);
        String request = mapper.writeValueAsString(newOneDto);

        when(service.update(newOne)).thenReturn(newOne);


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/customers/2")
                .contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isOk()).andReturn();

        Mockito.verify(service, times(1)).update(newOne);
        Assertions.assertEquals(request, result.getResponse().getContentAsString());

        //Unsuccessful update
        when(service.update(newOne))
                .thenThrow(new Exception("Cannot update, " + newOne.toString() + " does not exist"));

        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.put("/customers/2")
                        .contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().is4xxClientError()).andReturn();

        Mockito.verify(service, times(2)).update(newOne);
    }

    @Test
    public void getByIdTest() throws Exception {
        //Successful
        Customer newOne = new Customer(2L, "Lukas", "lukas@test.cz", "Praha", 100L);
        CustomerDto newOneDto = new CustomerDto(2L, "Lukas", "lukas@test.cz", "Praha", 100L);

        when(service.readById(2L)).thenReturn(Optional.of(newOne));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/customers/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        Mockito.verify(service, times(1)).readById(2L);
        Assertions.assertEquals(mapper.writeValueAsString(newOneDto), result.getResponse().getContentAsString());
        //Unsuccessful
        when(service.readById(2L)).thenReturn(Optional.empty());
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.get("/customers/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andReturn();

        Mockito.verify(service, times(2)).readById(2L);
    }
    @Test
    public void deleteByIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(service, times(1)).deleteById(2L);
    }
    @Test
    public void getAllTest() throws Exception {
        //2 Customers
        Customer newOne = new Customer(2L, "Lukas", "lukas@test.cz", "Praha", 100L);
        Customer secOne = new Customer(2L, "David", "lukas@test.cz", "Praha", 1000L);
        CustomerDto newOneDto = new CustomerDto(2L, "Lukas", "lukas@test.cz", "Praha", 100L);
        CustomerDto secOneDto = new CustomerDto(2L, "David", "lukas@test.cz", "Praha", 1000L);
        List<Customer> ret = new ArrayList<>();
        ret.add(newOne);
        ret.add(secOne);

        when(service.readAll()).thenReturn(ret);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        Mockito.verify(service, times(1)).readAll();
        String response = result.getResponse().getContentAsString();
        Assertions.assertTrue(response.contains(mapper.writeValueAsString(newOneDto)));
        Assertions.assertTrue(response.contains(mapper.writeValueAsString(secOneDto)));

        //0 Customers
        when(service.readAll()).thenReturn(new ArrayList<>());
        MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.get("/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        Mockito.verify(service, times(2)).readAll();
        String response2 = result2.getResponse().getContentAsString();
        Assertions.assertEquals("[]", response2);
    }



}
