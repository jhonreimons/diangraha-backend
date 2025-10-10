package com.diangraha_backend.diangraha_backend.controller;

import com.diangraha_backend.diangraha_backend.dto.AchievementResponse;
import com.diangraha_backend.diangraha_backend.service.AchievementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class AchievementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AchievementService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllAchievements() throws Exception {
        // Arrange
        AchievementResponse response1 = AchievementResponse.builder()
                .id(1L)
                .title("Achievement 1")
                .imageUrl("url1")
                .build();
        AchievementResponse response2 = AchievementResponse.builder()
                .id(2L)
                .title("Achievement 2")
                .imageUrl("url2")
                .build();
        List<AchievementResponse> responses = Arrays.asList(response1, response2);
        when(service.getAllAchievements()).thenReturn(responses);

        // Act & Assert
        mockMvc.perform(get("/api/achievements"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Achievement 1"))
                .andExpect(jsonPath("$[0].imageUrl").value("url1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Achievement 2"))
                .andExpect(jsonPath("$[1].imageUrl").value("url2"));

        verify(service, times(1)).getAllAchievements();
    }

    @Test
    public void testGetLimitedAchievements() throws Exception {
        // Arrange
        AchievementResponse response = AchievementResponse.builder()
                .id(1L)
                .title("Achievement 1")
                .imageUrl("url1")
                .build();
        List<AchievementResponse> responses = Arrays.asList(response);
        when(service.getLimitedAchievements(5)).thenReturn(responses);

        // Act & Assert
        mockMvc.perform(get("/api/achievements/limit/5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Achievement 1"))
                .andExpect(jsonPath("$[0].imageUrl").value("url1"));

        verify(service, times(1)).getLimitedAchievements(5);
    }

    @Test
    public void testCreateAchievement() throws Exception {
        // Arrange
        AchievementResponse response = AchievementResponse.builder()
                .id(1L)
                .title("New Achievement")
                .imageUrl("uploaded-url")
                .build();
        when(service.create(any(), any())).thenReturn(response);

        MockMultipartFile imageFile = new MockMultipartFile("imageFile", "image.jpg", "image/jpeg", "image content".getBytes());

        // Act & Assert
        mockMvc.perform(multipart("/api/achievements")
                .file(imageFile)
                .param("title", "New Achievement"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Achievement"))
                .andExpect(jsonPath("$.imageUrl").value("uploaded-url"));

        verify(service, times(1)).create(any(), any());
    }

    @Test
    public void testUpdateAchievement() throws Exception {
        // Arrange
        AchievementResponse response = AchievementResponse.builder()
                .id(1L)
                .title("Updated Achievement")
                .imageUrl("updated-url")
                .build();
        when(service.update(eq(1L), any(), any())).thenReturn(response);

        MockMultipartFile imageFile = new MockMultipartFile("imageFile", "image.jpg", "image/jpeg", "image content".getBytes());

        MockMultipartHttpServletRequestBuilder builder = multipart("/api/achievements/1");
        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });

        // Act & Assert
        mockMvc.perform(builder
                .file(imageFile)
                .param("title", "Updated Achievement"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated Achievement"))
                .andExpect(jsonPath("$.imageUrl").value("updated-url"));

        verify(service, times(1)).update(eq(1L), any(), any());
    }

    @Test
    public void testDeleteAchievement() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/achievements/1"))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteAchievement(1L);
    }
}
