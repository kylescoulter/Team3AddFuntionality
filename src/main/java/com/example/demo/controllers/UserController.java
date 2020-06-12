@Controller
public class UserController {
     
    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "add-user";
    }
     
    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }
         
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/index";
    }
 
    @GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
     
		model.addAttribute("user", user);
		return "update-user";
	}
	
	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") long id, @Valid User user, 
		BindingResult result, Model model) {
			if (result.hasErrors()) {
				user.setId(id);
				return "update-user";
			}
         
			userRepository.save(user);
			model.addAttribute("users", userRepository.findAll());
			return "redirect:/index";
}
     
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		userRepository.delete(user);
		model.addAttribute("users", userRepository.findAll());
		return "index";
	}
}