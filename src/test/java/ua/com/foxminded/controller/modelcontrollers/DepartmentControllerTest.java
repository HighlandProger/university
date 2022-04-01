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
import ua.com.foxminded.model.Department;
import ua.com.foxminded.service.DepartmentService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
class DepartmentControllerTest {

    private static final String TEMPLATE_URL = "/departments";
    private static final String VIEW_FOLDER_NAME = "departments";
    private static final String INDEX_VIEW = "/index";
    private static final String NEW_VIEW = "/new";
    private static final String EDIT_VIEW = "/edit";
    private static final String ENTITY_ATTRIBUTE_NAME = "entity";
    private static final String ENTITIES_ATTRIBUTE_NAME = "entities";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SLASH = "/";

    private final Department randomDepartment = new Department(1L, "IT");
    private final List<Department> randomDepartments = Arrays.asList(
        new Department(1L, "IT"),
        new Department(2L, "History")
    );

    @Mock
    DepartmentService departmentService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new DepartmentController(departmentService)).build();
    }

    @Test
    void index_shouldReturnDepartmentsIndexView() throws Exception {

        when(departmentService.getAll()).thenReturn(randomDepartments);
        mockMvc.perform(get(TEMPLATE_URL))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_FOLDER_NAME + INDEX_VIEW))
            .andExpect(model().attribute(ENTITIES_ATTRIBUTE_NAME, hasSize(randomDepartments.size())))
            .andExpect(model().attribute(ENTITIES_ATTRIBUTE_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(randomDepartments.get(0).getId())),
                    hasProperty(NAME, is(randomDepartments.get(0).getName()))
                )
            )))
            .andExpect(model().attribute(ENTITIES_ATTRIBUTE_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(randomDepartments.get(randomDepartments.size() - 1).getId())),
                    hasProperty(NAME, is(randomDepartments.get(randomDepartments.size() - 1).getName()))
                )
            )));

        verify(departmentService, times(1)).getAll();
        verifyNoMoreInteractions(departmentService);
    }

    @Test
    void newEntity_shouldReturnNewEntityView() throws Exception {

        mockMvc.perform(get(TEMPLATE_URL + NEW_VIEW))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_FOLDER_NAME + EDIT_VIEW))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, nullValue())))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(NAME, nullValue())));
    }

    @Test
    void edit_shouldReturnDepartmentsEditView() throws Exception {

        when(departmentService.getById(randomDepartment.getId())).thenReturn(randomDepartment);

        mockMvc.perform(get(TEMPLATE_URL + SLASH + randomDepartment.getId() + EDIT_VIEW)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_FOLDER_NAME + EDIT_VIEW))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, is(randomDepartment.getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(NAME, is(randomDepartment.getName()))));

        verify(departmentService, times(1)).getById(randomDepartment.getId());
        verifyNoMoreInteractions(departmentService);
    }

    @Test
    void create_shouldCreateDepartmentAndRedirect() throws Exception {

        when(departmentService.create(new Department(randomDepartment.getName()))).thenReturn(randomDepartment);

        mockMvc.perform(post(TEMPLATE_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(NAME, String.valueOf(randomDepartment.getName())))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, nullValue())))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(NAME, is(randomDepartment.getName()))));

        verify(departmentService, times(1)).create(new Department(randomDepartment.getName()));
        verifyNoMoreInteractions(departmentService);
    }

    @Test
    void update_shouldUpdateDepartmentAndRedirect() throws Exception {

        String changedName = "Math";
        Department changedDepartment = new Department(randomDepartment.getId(), changedName);

        mockMvc.perform(put(TEMPLATE_URL + SLASH + randomDepartment.getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(NAME, changedDepartment.getName()))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, is(randomDepartment.getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(NAME, is(changedDepartment.getName()))));

        verify(departmentService, times(1)).update(changedDepartment);
        verifyNoMoreInteractions(departmentService);
    }

    @Test
    void delete_shouldDeleteDepartmentAndRedirect() throws Exception {

        mockMvc.perform(delete(TEMPLATE_URL + SLASH + randomDepartment.getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(ID, String.valueOf(randomDepartment.getId())))
            .andExpect(status().is3xxRedirection());

        verify(departmentService, times(1)).delete(randomDepartment.getId());
        verifyNoMoreInteractions(departmentService);
    }
}
