package br.ce.wcaquino.taskbackend.controller;
import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;

import java.time.LocalDate;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.utils.ValidationException;

import org.junit.Assert;

public class TaskControllerTest {
	// os mock criado são injetados no @InjectMocks no caso o controller
	@Mock
	private TaskRepo taskRepo;

	@InjectMocks
	private TaskController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() {
		Task todo = new Task();
	//	todo.setTask("Descrição");
		todo.setDueDate(LocalDate.now());
		
		try {
			controller.save(todo);
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the task description", e.getMessage());
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() {
		Task todo = new Task();
			todo.setTask("Descrição");
		//	todo.setDueDate(LocalDate.now());
				try {
				controller.save(todo);
			} catch (ValidationException e) {
				Assert.assertEquals("Fill the due date", e.getMessage());
			}
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		Task todo = new Task();
			todo.setTask("Descrição");
			todo.setDueDate(LocalDate.of(2010, 01, 01));
			
			try {
				controller.save(todo);
			} catch (ValidationException e) {
				Assert.assertEquals("Due date must not be in past", e.getMessage());
			}
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() throws ValidationException {
		Task todo = new Task();
		todo.setTask("Descrição");
		todo.setDueDate(LocalDate.now());
		
		controller.save(todo);
		
		Mockito.verify(taskRepo).save(todo);
		//verifica se durante o teste o taskRepo foi instanciado e chamado o metodo save comk o parametro todo
	}
	
}
