package ua.com.foxminded.controller.modelcontrollers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.com.foxminded.model.ClassRoom;
import ua.com.foxminded.service.ClassRoomService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
class ClassRoomControllerTest {

    private static final String TEMPLATE_URL = "/classrooms";
    private static final String VIEW_FOLDER_NAME = "classrooms";
    private static final String INDEX_VIEW = "/index";
    private static final String NEW_VIEW = "/new";
    private static final String EDIT_VIEW = "/edit";
    private static final String ENTITY_ATTRIBUTE_NAME = "entity";
    private static final String ENTITIES_ATTRIBUTE_NAME = "entities";
    private static final String ID = "id";
    private static final String CLASS_NUMBER = "classNumber";
    private static final String SLASH = "/";

    private final ClassRoom randomClassRoom = new ClassRoom(1L, "1");
    private final List<ClassRoom> randomClassRooms = Arrays.asList(new ClassRoom(1L, "1"), new ClassRoom(2L, "2"));

    @Mock
    ClassRoomService classRoomService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ClassRoomController(classRoomService)).build();
    }

    @Test
    void index_shouldReturnClassRoomsIndexView() throws Exception {

        when(classRoomService.getAll()).thenReturn(randomClassRooms);
        mockMvc.perform(get(TEMPLATE_URL)).andExpect(status().isOk()).andExpect(view().name(VIEW_FOLDER_NAME + INDEX_VIEW)).andExpect(model().attribute(ENTITIES_ATTRIBUTE_NAME, hasSize(randomClassRooms.size()))).andExpect(model().attribute(ENTITIES_ATTRIBUTE_NAME, hasItem(allOf(hasProperty(ID, is(randomClassRooms.get(0).getId())), hasProperty(CLASS_NUMBER, is(randomClassRooms.get(0).getClassNumber())))))).andExpect(model().attribute(ENTITIES_ATTRIBUTE_NAME, hasItem(allOf(hasProperty(ID, is(randomClassRooms.get(1).getId())), hasProperty(CLASS_NUMBER, is(randomClassRooms.get(1).getClassNumber()))))));

        verify(classRoomService, times(1)).getAll();
        verifyNoMoreInteractions(classRoomService);
    }

    @Test
    void newEntity_shouldReturnNewEntityView() throws Exception {

        mockMvc.perform(get(TEMPLATE_URL + NEW_VIEW)).andExpect(status().isOk()).andExpect(view().name(VIEW_FOLDER_NAME + EDIT_VIEW)).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, nullValue()))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(CLASS_NUMBER, nullValue())));
    }

    @Test
    void edit_shouldReturnClassRoomsEditView() throws Exception {

        when(classRoomService.getById(randomClassRoom.getId())).thenReturn(randomClassRoom);

        mockMvc.perform(get(TEMPLATE_URL + SLASH + randomClassRoom.getId() + EDIT_VIEW).contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk()).andExpect(view().name(VIEW_FOLDER_NAME + EDIT_VIEW)).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, is(randomClassRoom.getId())))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(CLASS_NUMBER, is(randomClassRoom.getClassNumber()))));

        verify(classRoomService, times(1)).getById(randomClassRoom.getId());
        verifyNoMoreInteractions(classRoomService);
    }

    @Test
    void create_shouldCreateClassRoomAndRedirect() throws Exception {

        when(classRoomService.create(new ClassRoom(randomClassRoom.getClassNumber()))).thenReturn(randomClassRoom);

        mockMvc.perform(post(TEMPLATE_URL).contentType(MediaType.APPLICATION_FORM_URLENCODED).param(CLASS_NUMBER, randomClassRoom.getClassNumber())).andExpect(status().is3xxRedirection()).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, nullValue()))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(CLASS_NUMBER, is(randomClassRoom.getClassNumber()))));

        verify(classRoomService, times(1)).create(new ClassRoom(randomClassRoom.getClassNumber()));
        verifyNoMoreInteractions(classRoomService);
    }

    @Test
    void update_shouldUpdateClassRoomAndRedirect() throws Exception {

        String changedClassNumber = "4";
        ClassRoom changedClassRoom = new ClassRoom(randomClassRoom.getId(), changedClassNumber);

        mockMvc.perform(put(TEMPLATE_URL + SLASH + randomClassRoom.getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(CLASS_NUMBER, changedClassRoom.getClassNumber()))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, is(randomClassRoom.getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(CLASS_NUMBER, is(changedClassRoom.getClassNumber()))));

        verify(classRoomService, times(1)).update(changedClassRoom);
        verifyNoMoreInteractions(classRoomService);
    }

    @Test
    void delete_shouldDeleteClassRoomAndRedirect() throws Exception {

        mockMvc.perform(delete(TEMPLATE_URL + SLASH + randomClassRoom.getId()).contentType(MediaType.APPLICATION_FORM_URLENCODED).param(ID, String.valueOf(randomClassRoom.getId()))).andExpect(status().is3xxRedirection());

        verify(classRoomService, times(1)).delete(randomClassRoom.getId());
        verifyNoMoreInteractions(classRoomService);
    }
}
