package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.LoginDTOpos;
import backend.dto.LoginDTOpre;
import backend.exception.AdminException;
import backend.service.AdminService;

@RestController
@RequestMapping("/seguranca")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@PostMapping("/autenticar")
	public ResponseEntity<LoginDTOpos> criarAutenticacao(@RequestBody LoginDTOpre loginDTO) throws AdminException{
		return ResponseEntity.ok(adminService.autenticar(loginDTO));
	}

}
