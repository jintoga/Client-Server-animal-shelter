/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnimalShelter;

import AnimalShelter.Core.Animal;
import AnimalShelter.Core.Expense;
import AnimalShelter.Core.Owner;
import AnimalShelter.Core.Profit;
import AnimalShelter.Core.Shelter;
import AnimalShelter.Core.Sponsor;
import AnimalShelter.Core.Staff;
import AnimalShelter.Core.Support_Type;
import AnimalShelter.Core.TempOwner;
import AnimalShelter.Core.Type_Animal;
import AnimalShelter.Core.Type_AnimalHelper;
import AnimalShelter.Core.Volunteer;
import AnimalShelter.Services.MyService;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.codec.binary.Base64;
import org.imgscalr.Scalr;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @author DAT
 */
@Controller
public class MyController extends WebMvcConfigurerAdapter {

    private final boolean isNotApproved = false;
    private final boolean isApproved = true;
    private final String tag_pk = "pk";
    private final String tag_name = "name";
    private final String tag_species = "species";
    private final String tag_breed = "breed";
    private final String tag_age = "age";
    private final String tag_weight = "weight";
    private final String tag_gender = "gender";
    private final String tag_sterilize = "sterilize";
    private final String tag_description = "description";
    private final String tag_latitude = "latitude";
    private final String tag_longitude = "longitude";

    private final String tag_image = "image";

    private final String tag_date = "date";
    private final String tag_location = "location";

    private final String typeLost = "Потерянное";
    private final String typeHomeless = "Бездомное";

    @Autowired
    private MyService svc;

    protected final Log log = LogFactory.getLog(getClass());

    private String getFile(Long pk_animal) throws FileNotFoundException, IOException {
        String stringOfDecodedImage = null;
        String myFile = "C:\\testIMG\\static\\img\\animals\\" + pk_animal + ".jpg";
        File file = new File(myFile);

        if (file.exists()) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedImage bi = ImageIO.read(file);
            ImageIO.write(bi, "JPG", baos);
            byte[] ba = baos.toByteArray();
            stringOfDecodedImage = Base64.encodeBase64String(ba);
            baos.close();
        }
        log.info("File exists2:" + file.exists());
        return stringOfDecodedImage;
    }

    //client load animals from server
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/loading")
    public Object clientLoadingFromServer(String data) {
        svc.filterAge();
        svc.filterGender();
        svc.filterSterilized();
        JSONArray jsonArray = null;

        JSONObject jSONObject;
        if (svc.animalsIsApprovedByAdmin().size() > 0) {
            jsonArray = new JSONArray();
            for (Animal animalToShow : svc.animalsIsApprovedByAdmin()) {
                jSONObject = new JSONObject();
                jSONObject.put(tag_pk, animalToShow.getPk_animal());
                jSONObject.put(tag_name, animalToShow.getName());
                jSONObject.put(tag_species, animalToShow.getType_animal().getTitle());
                jSONObject.put(tag_breed, animalToShow.getBreed());
                jSONObject.put(tag_gender, animalToShow.getShowGender());
                jSONObject.put(tag_age, animalToShow.getShowAge());
                jSONObject.put(tag_weight, animalToShow.getWeight());
                jSONObject.put(tag_sterilize, animalToShow.getSterilized_status());
                jSONObject.put(tag_description, animalToShow.getDescription());
//                try {
//                    jSONObject.put(tag_image, getFile(animalToShow.getPk_animal()));
//                    log.info(getFile(animalToShow.getPk_animal()));
//                } catch (IOException ex) {
//                    Logger.getLogger(MyController.class.getName()).log(Level.SEVERE, null, ex);
//                }
                jsonArray.add(jSONObject);
            }
        }

        return jsonArray;
    }

    //client load lost animals from server
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/loading_lost_animals")
    public Object clientLoadingLostAnimalsFromServer(String data) {
        svc.filterAge();
        svc.filterGender();
        svc.filterSterilized();
        JSONArray jsonArray = null;

        JSONObject jSONObject;
        if (svc.animalsIsApprovedByAdmin().size() > 0) {
            jsonArray = new JSONArray();
            for (Animal animalToShow : svc.animalsIsLost(typeLost)) {
                jSONObject = new JSONObject();
                jSONObject.put(tag_pk, animalToShow.getPk_animal());
                jSONObject.put(tag_species, animalToShow.getType_animal().getTitle());
                jSONObject.put(tag_date, animalToShow.getLast_date_seen());
                jSONObject.put(tag_location, animalToShow.getLast_location());
                jSONObject.put(tag_description, animalToShow.getDescription());
                jSONObject.put(tag_latitude, animalToShow.getLatitude());
                jSONObject.put(tag_longitude, animalToShow.getLongitude());
//                try {
//                    jSONObject.put(tag_image, getFile(animalToShow.getPk_animal()));
//                    log.info(getFile(animalToShow.getPk_animal()));
//                } catch (IOException ex) {
//                    Logger.getLogger(MyController.class.getName()).log(Level.SEVERE, null, ex);
//                }
                jsonArray.add(jSONObject);
            }
        }

        return jsonArray;
    }

    //notification
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/notification")
    public Object notification() {
        Object amount = (Object) svc.animalsWaitForApprove().size();
        log.info(amount.toString());
        String myJSon = '"' + amount.toString() + '"';
        log.info("myJSON:" + myJSon);
        return amount;
        //return svc.filterAllGender(v2);
    }

    //approve animal
    @RequestMapping(method = RequestMethod.POST, value = "/animals/{pk_animal}/approve")
    public String approveAnimal(@PathVariable Long pk_animal) throws IOException {
        svc.approveAnimal(pk_animal);
        String pathSource = "C:\\testIMG\\static\\img" + File.separator + "temp_animals\\";
        String pathTarget = "C:\\testIMG\\static\\img" + File.separator + "animals\\";
        File fileSource = new File(pathSource + File.separator + pk_animal + ".jpg");
        File fileTarget = new File(pathTarget + File.separator + pk_animal + ".jpg");

        if (!fileTarget.exists()) {
            fileTarget.mkdirs();
        }
        Files.move(fileSource.toPath(), fileTarget.toPath(), StandardCopyOption.REPLACE_EXISTING);

        String path = "C:\\testIMG\\static\\img" + File.separator + "animals";
        makeThumbnails(fileTarget, path, pk_animal);
        return "redirect:/admin";
    }

    //admin page
    @RequestMapping(method = RequestMethod.GET, value = "/admin")
    public String admin(Model vars) {
        vars.addAttribute("type_animal", svc.type_animal());
        vars.addAttribute("animals", svc.animalsWaitForApprove());

        svc.filterGender();
        svc.filterSterilized();
        svc.filterAge();
        return "admin";
    }

    //animals page
    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String index(Model vars) {
        vars.addAttribute("type_animal", svc.type_animal());
        vars.addAttribute("animals", svc.animalsIsApprovedByAdmin());

        svc.filterGender();
        svc.filterSterilized();
        svc.filterAge();
        return "animals";
    }

    //profits page
    @RequestMapping(method = RequestMethod.GET, value = "/profits")
    public String profits(Model vars) {
        vars.addAttribute("sponsors", svc.sponsors());
        vars.addAttribute("support_types", svc.support_types());
        vars.addAttribute("profits", svc.profits());
        return "profits";
    }

    //support types page
    @RequestMapping(method = RequestMethod.GET, value = "/support_types")
    public String support_types(Model vars) {
        vars.addAttribute("support_types", svc.support_types());

        return "support_types";
    }

    //add 1 profit
    @RequestMapping(method = RequestMethod.POST, value = "/profits/add")
    public String addNewProfit(@RequestParam Long pk_sponsor, @RequestParam Long pk_support_type, int amount, String description, String date_receive) {
//        Sponsor sponsor = svc.sponsor(pk_sponsor);
//        Support_Type support_type = svc.support_type(pk_support_type);

        log.info("support_type:" + pk_support_type);
        log.info("sponsor:" + pk_sponsor);
        Profit profit = svc.addProfit(pk_sponsor, pk_support_type, amount, description, date_receive);
        return "redirect:/profits";
    }

    //filter
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/filter")
    public Object filter(Long v1, Integer v2, Integer v3) {
        return svc.getListHelper(v1, v2, v3, MyService.isApprovedByAdmin);
        //return svc.filterAllGender(v2);
    }

    //show 1 animal
    @RequestMapping(method = RequestMethod.GET, value = "/animals/{pk_animal}")
    public String animal(@PathVariable Long pk_animal, Model vars
    ) {
        Animal animal = svc.animal(pk_animal);
        Type_Animal type_animal = animal.getType_animal();
        vars.addAttribute("animal", animal);
        vars.addAttribute("type_animal", type_animal);

        svc.filterGender();
        svc.filterSterilized();
        svc.filterAge();
        return "animal";
    }

    //show Modal Image animal
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/animals/{pk_animal}/showAnimal")
    public Object animal(Long pk_animal) {
        Animal animal = svc.animal(pk_animal);
        Type_AnimalHelper animal_temp = new Type_AnimalHelper(animal);

        return animal_temp;
    }

    //show Modal Image and infor temp animal
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/animals/{pk_animal}/showTempAnimal")
    public Object temp_animal(Long pk_animal) {
        Animal animal = svc.animal(pk_animal);
        Type_AnimalHelper animal_temp = new Type_AnimalHelper(animal);

        return animal_temp;
    }

    //edit animal page
    @RequestMapping(method = RequestMethod.GET, value = "/animals/{pk_animal}/edit")
    public String editAnimal(@PathVariable Long pk_animal, Model vars) {
        Animal animal = svc.animal(pk_animal);
        Type_Animal type_animal = animal.getType_animal();
        vars.addAttribute("animal", animal);
        vars.addAttribute("type_animal", type_animal);
        vars.addAttribute("all_type_animal", svc.type_animal());

        return "animal_edit";
    }

//    @ResponseBody
//    @RequestMapping(method = RequestMethod.POST, value = "/animals/add")
//    public Object TestAdd(@RequestParam String type_animal, float weight, int age
//    ) {
//        Type_Animal animalType = svc.findAnimalPK(type_animal);
//
//        Long pk_type_animal = animalType.getPk_type_animal();
//
//        Animal animal = svc.addAnimal(pk_type_animal, null, null, 1, null, null, weight, null, null, age, null, null);
//        // Animal animal = svc.addAnimal(pk_type_animal, weight,age);
//        return "redirect:/";
//    }
    //add 1 animal
    @RequestMapping(method = RequestMethod.POST, value = "/animals/add")
    public String addNewAnimal(@RequestParam("file") MultipartFile file, @RequestParam String type_animal, String name, String type, int gender, float weight, String breed, int age, String description, int sterilized) {
        Type_Animal animalType = svc.findAnimalPK(type_animal);

        Long pk_type_animal = animalType.getPk_type_animal();

        Animal animal = svc.addAnimal(pk_type_animal, name, type, gender, weight, breed, age, description, sterilized, isApproved, null);
        // Animal animal = svc.addAnimal(pk_type_animal, weight,age);
        svc.filterGender();
        svc.filterSterilized();
        svc.filterAge();
        addImage(file, "animals", animal.getPk_animal());
        return "redirect:/";
        //return "redirect:/";
    }

    //delete 1 animal
    @RequestMapping(method = RequestMethod.DELETE, value = "/animals/{pk_animal}")
    public String deleteAnimal(@PathVariable Long pk_animal) {
        svc.deleteAnimal(pk_animal);
        deleteImage("animals", pk_animal);
        return "redirect:/";
    }

    //delete 1 non approved animal
    @RequestMapping(method = RequestMethod.DELETE, value = "/animals/{pk_animal}/delete")
    public String deleteNotApprovedAnimal(@PathVariable Long pk_animal) {
        svc.deleteAnimal(pk_animal);
        deleteImage("temp_animals", pk_animal);
        return "redirect:/admin";
    }

    //edit 1 animal
    @RequestMapping(method = RequestMethod.POST, value = "/animals/{pk_animal}")
    public String editAnimal(@PathVariable Long pk_animal, @RequestParam Long pk_type_animal, String name, String type, int gender, float weight, String breed, Integer age, String description, int sterilized, @RequestParam("file") MultipartFile file) {
        Animal animal = svc.animal(pk_animal);
        svc.updateAnimal(pk_animal, pk_type_animal, name, type, gender, weight, breed, age, description, sterilized);
        addImage(file, "animals", animal.getPk_animal());
        return "redirect:/animals/{pk_animal}";
    }

    //tempowners page
    @RequestMapping(method = RequestMethod.GET, value = "/temp_owners")
    public String temp_owners(Model vars) {
        vars.addAttribute("temp_owners", svc.temp_owners());

        return "temp_owners";
    }

    //owners page
    @RequestMapping(method = RequestMethod.GET, value = "/owners")
    public String owners(Model vars) {
        vars.addAttribute("owners", svc.owners());

        return "owners";
    }

    //show 1 owner
    @RequestMapping(method = RequestMethod.GET, value = "/owners/{pk_owner}")
    public String owner(@PathVariable Long pk_owner, Model vars
    ) {
        Owner owner = svc.owner(pk_owner);

        vars.addAttribute("owner", owner);
        return "owner";
    }

    //add 1 owner
    @RequestMapping(method = RequestMethod.POST, value = "/owners/add")
    public String addNewOwner(@RequestParam("file") MultipartFile file, @RequestParam String name, int telephone, String address, int amount_of_animal) {
        log.info(file);
        Owner owner = svc.addOwner(name, telephone, address, amount_of_animal);
        addImage(file, "owners", owner.getPk_owner());
        return "redirect:/owners";
    }

    //add 1 tempowner
    @RequestMapping(method = RequestMethod.POST, value = "/temp_owners/add")
    public String addNewTempOwner(@RequestParam("file") MultipartFile file, @RequestParam String name, int telephone, String address, int amount_of_animal) {

        TempOwner temp_owner = svc.addTempOwner(name, telephone, address, amount_of_animal);
        addImage(file, "temp_owners", temp_owner.getPk_temp_owner());
        return "redirect:/temp_owners";
    }

    //show 1 tempowner
    @RequestMapping(method = RequestMethod.GET, value = "/temp_owners/{pk_temp_owner}")
    public String temp_owner(@PathVariable Long pk_temp_owner, Model vars
    ) {
        TempOwner temp_owner = svc.temp_owner(pk_temp_owner);

        vars.addAttribute("temp_owner", temp_owner);
        return "temp_owner";
    }

    //edit tempowner page
    @RequestMapping(method = RequestMethod.GET, value = "/temp_owners/{pk_temp_owner}/edit")
    public String editTempOwnerPage(@PathVariable Long pk_temp_owner, Model vars) {
        TempOwner temp_owner = svc.temp_owner(pk_temp_owner);

        vars.addAttribute("temp_owner", temp_owner);

        return "temp_owner_edit";
    }

    //delete 1 tempowner
    @RequestMapping(method = RequestMethod.DELETE, value = "/temp_owners/{pk_temp_owner}")
    public String deleteTempOwner(@PathVariable Long pk_temp_owner) {
        svc.deleteTempOwner(pk_temp_owner);
        deleteImage("temp_owners", pk_temp_owner);
        return "redirect:/temp_owners";
    }

    //edit owner page
    @RequestMapping(method = RequestMethod.GET, value = "/owners/{pk_owner}/edit")
    public String editOwnerPage(@PathVariable Long pk_owner, Model vars) {
        Owner owner = svc.owner(pk_owner);

        vars.addAttribute("owner", owner);

        return "owner_edit";
    }

    //delete 1 owner
    @RequestMapping(method = RequestMethod.DELETE, value = "/owners/{pk_owner}")
    public String deleteOwner(@PathVariable Long pk_owner) {
        svc.deleteOwner(pk_owner);
        deleteImage("owners", pk_owner);
        return "redirect:/owners";
    }

    //edit 1 owner
    @RequestMapping(method = RequestMethod.POST, value = "/owners/{pk_owner}")
    public String editOwner(@RequestParam("file") MultipartFile file, @PathVariable Long pk_owner, @RequestParam String name, int telephone, String address, int amount_of_animal) {
        Owner owner = svc.owner(pk_owner);
        svc.updateOwner(pk_owner, name, telephone, address, amount_of_animal);
        addImage(file, "owners", owner.getPk_owner());
        return "redirect:/owners/{pk_owner}";
    }

    //edit 1 tempowner
    @RequestMapping(method = RequestMethod.POST, value = "/temp_owners/{pk_temp_owner}")
    public String editTempOwner(@RequestParam("file") MultipartFile file, @PathVariable Long pk_temp_owner, @RequestParam String name, int telephone, String address, int amount_of_animal) {
        TempOwner temp_owner = svc.temp_owner(pk_temp_owner);
        svc.updateTempOwner(pk_temp_owner, name, telephone, address, amount_of_animal);
        addImage(file, "temp_owners", temp_owner.getPk_temp_owner());
        return "redirect:/temp_owners/{pk_temp_owner}";
    }

    //shelters page
    @RequestMapping(method = RequestMethod.GET, value = "/shelters")
    public String shelters(Model vars) {
        vars.addAttribute("shelters", svc.shelters());

        return "shelters";
    }

    //sponsors page
    @RequestMapping(method = RequestMethod.GET, value = "/sponsors")
    public String sponsors(Model vars) {
        vars.addAttribute("sponsors", svc.sponsors());
        svc.filterOrganization();
        return "sponsors";
    }

    //staves page
    @RequestMapping(method = RequestMethod.GET, value = "/staves")
    public String staves(Model vars) {
        vars.addAttribute("staves", svc.staves());

        return "staves";
    }

    //expenses page
    @RequestMapping(method = RequestMethod.GET, value = "/expenses")
    public String expenses(Model vars) {
        vars.addAttribute("expenses", svc.expenses());

        return "expenses";
    }

    //volunteers page
    @RequestMapping(method = RequestMethod.GET, value = "/volunteers")
    public String volunteer(Model vars) {
        vars.addAttribute("volunteers", svc.volunteers());

        return "volunteers";
    }

    //add 1 shelter
    @RequestMapping(method = RequestMethod.POST, value = "/shelters/add")
    public String addNewShelter(@RequestParam("file") MultipartFile file, @RequestParam String name, int telephone, String address, int seat, int free_seat, String site, String email, String description) {

        Shelter shelter = svc.addShelter(name, telephone, address, seat, free_seat, site, email, description);
        addImage(file, "shelters", shelter.getPk_shelter());
        return "redirect:/shelters";
    }

    //add 1 support type
    @RequestMapping(method = RequestMethod.POST, value = "/support_types/add")
    public String addNewSupport_type(@RequestParam String title) {

        Support_Type support_type = svc.addSupport_type(title);
        return "redirect:/support_types";
    }

    //add 1 sponsor
    @RequestMapping(method = RequestMethod.POST, value = "/sponsors/add")
    public String addNewSponsor(@RequestParam("file") MultipartFile file, @RequestParam String name, int telephone, String address, String site, String email, String description, int is_organization) {

        Sponsor sponsor = svc.addSponsor(name, telephone, address, site, email, description, is_organization);
        addImage(file, "sponsors", sponsor.getPk_sponsor());
        return "redirect:/sponsors";
    }

    //add 1 staff
    @RequestMapping(method = RequestMethod.POST, value = "/staves/add")
    public String addNewStaff(@RequestParam("file") MultipartFile file, @RequestParam String name, String career, Integer telephone, String date_of_birth, String address, String description) {
        log.info(date_of_birth);
        Staff staff = svc.addStaff(name, career, telephone, date_of_birth, address, description);
        addImage(file, "staves", staff.getPk_staff());
        return "redirect:/staves";
    }

    //add 1 expense
    @RequestMapping(method = RequestMethod.POST, value = "/expenses/add")
    public String addNewExpense(@RequestParam String product, int price, String organization, String date_use, String description) {

        Expense expense = svc.addExpense(product, price, organization, date_use, description);
        return "redirect:/expenses";
    }

    //add 1 volunteer
    @RequestMapping(method = RequestMethod.POST, value = "/volunteers/add")
    public String addNewVolunteer(@RequestParam("file") MultipartFile file, @RequestParam String name, String career, Integer telephone, String date_of_birth, String address, String description) {
        log.info(date_of_birth);
        Volunteer volunteer = svc.addVolunteer(name, career, telephone, date_of_birth, address, description);
        addImage(file, "volunteers", volunteer.getPk_volunteer());
        return "redirect:/volunteers";
    }

    //show 1 staff
    @RequestMapping(method = RequestMethod.GET, value = "/staves/{pk_staff}")
    public String staff(@PathVariable Long pk_staff, Model vars
    ) {
        Staff staff = svc.staff(pk_staff);

        vars.addAttribute("staff", staff);
        return "staff";
    }

    //show 1 staff
    @RequestMapping(method = RequestMethod.GET, value = "/expenses/{pk_expense}")
    public String expense(@PathVariable Long pk_expense, Model vars
    ) {
        Expense expense = svc.expense(pk_expense);

        vars.addAttribute("expense", expense);
        return "expense";
    }

    //show 1 profit
    @RequestMapping(method = RequestMethod.GET, value = "/profits/{pk_profit}")
    public String profit(@PathVariable Long pk_profit, Model vars
    ) {
        Profit profit = svc.profit(pk_profit);

        vars.addAttribute("profit", profit);
        return "profit";
    }

    //show 1 volunteer
    @RequestMapping(method = RequestMethod.GET, value = "/volunteers/{pk_volunteer}")
    public String volunteer(@PathVariable Long pk_volunteer, Model vars
    ) {
        Volunteer volunteer = svc.volunteer(pk_volunteer);

        vars.addAttribute("volunteer", volunteer);
        return "volunteer";
    }

    //delete 1 staff
    @RequestMapping(method = RequestMethod.DELETE, value = "/staves/{pk_staff}")
    public String deleteStaff(@PathVariable Long pk_staff) {
        svc.deleteStaff(pk_staff);
        deleteImage("staves", pk_staff);
        return "redirect:/staves";
    }

    //delete 1 support type
    @RequestMapping(method = RequestMethod.DELETE, value = "/support_types/{pk_support_type}")
    public String deleteSupport_type(@PathVariable Long pk_support_type) {
        svc.deleteSupport_type(pk_support_type);
        return "redirect:/support_types";
    }

    //delete 1 expense
    @RequestMapping(method = RequestMethod.DELETE, value = "/expenses/{pk_expense}")
    public String deleteExpense(@PathVariable Long pk_expense) {
        svc.deleteExpense(pk_expense);
        return "redirect:/expenses";
    }

    //delete 1 profit
    @RequestMapping(method = RequestMethod.DELETE, value = "/profits/{pk_profit}")
    public String deleteProfit(@PathVariable Long pk_profit) {
        svc.deleteProfit(pk_profit);
        return "redirect:/profits";
    }

    //delete 1 volunteer
    @RequestMapping(method = RequestMethod.DELETE, value = "/volunteers/{pk_volunteer}")
    public String deleteVolunteer(@PathVariable Long pk_volunteer) {
        svc.deleteVolunteer(pk_volunteer);
        deleteImage("volunteers", pk_volunteer);
        return "redirect:/volunteers";
    }

    //edit staff page
    @RequestMapping(method = RequestMethod.GET, value = "/staves/{pk_staff}/edit")
    public String editStaffPage(@PathVariable Long pk_staff, Model vars) {
        Staff staff = svc.staff(pk_staff);

        vars.addAttribute("staff", staff);

        return "staff_edit";
    }

    //edit profit page
    @RequestMapping(method = RequestMethod.GET, value = "/profits/{pk_profit}/edit")
    public String editProfitPage(@PathVariable Long pk_profit, Model vars) {
        Profit profit = svc.profit(pk_profit);

        Sponsor sponsor = profit.getSponsor();
        Support_Type support_type = profit.getSupporttype();
        vars.addAttribute("sponsor", sponsor);
        vars.addAttribute("support_type", support_type);

        vars.addAttribute("support_types", svc.support_types());
        vars.addAttribute("sponsors", svc.sponsors());
        vars.addAttribute("profit", profit);

        return "profit_edit";
    }

    //edit volunteer page
    @RequestMapping(method = RequestMethod.GET, value = "/volunteers/{pk_volunteer}/edit")
    public String editVolunteerPage(@PathVariable Long pk_volunteer, Model vars) {
        Volunteer volunteer = svc.volunteer(pk_volunteer);

        vars.addAttribute("volunteer", volunteer);

        return "volunteer_edit";
    }

    //edit expense page
    @RequestMapping(method = RequestMethod.GET, value = "/expenses/{pk_expense}/edit")
    public String editExpensePage(@PathVariable Long pk_expense, Model vars) {
        Expense expense = svc.expense(pk_expense);

        vars.addAttribute("expense", expense);

        return "expense_edit";
    }

    //edit 1 staff
    @RequestMapping(method = RequestMethod.POST, value = "/staves/{pk_staff}")
    public String editStaff(@RequestParam("file") MultipartFile file, @PathVariable Long pk_staff, @RequestParam String name, String career, Integer telephone, String date_of_birth, String address, String description) {
        Staff staff = svc.staff(pk_staff);
        svc.updateStaff(pk_staff, name, career, telephone, date_of_birth, address, description);
        addImage(file, "staves", staff.getPk_staff());
        return "redirect:/staves/{pk_staff}";
    }

    //edit 1 profit
    @RequestMapping(method = RequestMethod.POST, value = "/profits/{pk_profit}")
    public String editProfit(@PathVariable Long pk_profit, Long pk_sponsor, Long pk_support_type, int amount, String description, String date_receive) {
        Profit profit = svc.profit(pk_profit);
        svc.updateProfit(pk_profit, pk_sponsor, pk_support_type, amount, description, date_receive);
        return "redirect:/profits/{pk_profit}";
    }

    //edit 1 expense
    @RequestMapping(method = RequestMethod.POST, value = "/expenses/{pk_expense}")
    public String editExpense(@PathVariable Long pk_expense, String product, int price, String organization, String date_use, String description) {
        //Expense expense = svc.expense(pk_expense);
        svc.updateExpense(pk_expense, product, price, organization, date_use, description);
        return "redirect:/expenses/{pk_expense}";
    }

    //edit 1 volunteer
    @RequestMapping(method = RequestMethod.POST, value = "/volunteers/{pk_volunteer}")
    public String editVolunteer(@RequestParam("file") MultipartFile file, @PathVariable Long pk_volunteer, @RequestParam String name, String career, Integer telephone, String date_of_birth, String address, String description) {
        Volunteer volunteer = svc.volunteer(pk_volunteer);
        svc.updateVolunteer(pk_volunteer, name, career, telephone, date_of_birth, address, description);
        addImage(file, "volunteers", volunteer.getPk_volunteer());
        return "redirect:/volunteers/{pk_volunteer}";
    }

    //show 1 shelter
    @RequestMapping(method = RequestMethod.GET, value = "/shelters/{pk_shelter}")
    public String shelter(@PathVariable Long pk_shelter, Model vars
    ) {
        Shelter shelter = svc.shelter(pk_shelter);

        vars.addAttribute("shelter", shelter);
        return "shelter";
    }

    //show 1 support_type
    @RequestMapping(method = RequestMethod.GET, value = "/support_types/{pk_support_type}")
    public String support_type(@PathVariable Long pk_support_type, Model vars
    ) {
        Support_Type support_type = svc.support_type(pk_support_type);

        vars.addAttribute("support_type", support_type);
        return "support_type";
    }

    //show 1 sponsor
    @RequestMapping(method = RequestMethod.GET, value = "/sponsors/{pk_sponsor}")
    public String sponsor(@PathVariable Long pk_sponsor, Model vars
    ) {
        Sponsor sponsor = svc.sponsor(pk_sponsor);
        svc.filterOrganization();
        vars.addAttribute("sponsor", sponsor);
        return "sponsor";
    }

    //delete 1 shelter
    @RequestMapping(method = RequestMethod.DELETE, value = "/shelters/{pk_shelter}")
    public String deleteShelter(@PathVariable Long pk_shelter) {
        svc.deleteShelter(pk_shelter);
        deleteImage("shelters", pk_shelter);
        return "redirect:/shelters";
    }

    //delete 1 sponsor
    @RequestMapping(method = RequestMethod.DELETE, value = "/sponsors/{pk_sponsor}")
    public String deleteSponsor(@PathVariable Long pk_sponsor) {
        svc.deleteSponsor(pk_sponsor);
        deleteImage("sponsors", pk_sponsor);
        return "redirect:/sponsors";
    }

    //edit shelter page
    @RequestMapping(method = RequestMethod.GET, value = "/shelters/{pk_shelter}/edit")
    public String editShelterPage(@PathVariable Long pk_shelter, Model vars) {
        Shelter shelter = svc.shelter(pk_shelter);

        vars.addAttribute("shelter", shelter);

        return "shelter_edit";
    }

    //edit support_type page
    @RequestMapping(method = RequestMethod.GET, value = "/support_types/{pk_support_type}/edit")
    public String editSupport_typePage(@PathVariable Long pk_support_type, Model vars) {
        Support_Type support_type = svc.support_type(pk_support_type);

        vars.addAttribute("support_type", support_type);

        return "support_type_edit";
    }

    //edit sponsor page
    @RequestMapping(method = RequestMethod.GET, value = "/sponsors/{pk_sponsor}/edit")
    public String editSponsorPage(@PathVariable Long pk_sponsor, Model vars) {
        Sponsor sponsor = svc.sponsor(pk_sponsor);

        vars.addAttribute("sponsor", sponsor);

        return "sponsor_edit";
    }

    //edit 1 shelter
    @RequestMapping(method = RequestMethod.POST, value = "/shelters/{pk_shelter}")
    public String editShelter(@RequestParam("file") MultipartFile file, @PathVariable Long pk_shelter, @RequestParam String name, int telephone, String address, int seat, int free_seat, String site, String email, String description) {
        Shelter shelter = svc.shelter(pk_shelter);
        svc.updateShelter(pk_shelter, name, telephone, address, seat, free_seat, site, email, description);
        addImage(file, "shelters", shelter.getPk_shelter());
        return "redirect:/shelters/{pk_shelter}";
    }

    //edit 1 support_type
    @RequestMapping(method = RequestMethod.POST, value = "/support_types/{pk_support_type}")
    public String editShelter(@PathVariable Long pk_support_type, @RequestParam String title) {

        svc.updateSupport_type(pk_support_type, title);
        return "redirect:/support_types/{pk_support_type}";
    }

    //edit 1 sponsor
    @RequestMapping(method = RequestMethod.POST, value = "/sponsors/{pk_sponsor}")
    public String editSponsor(@RequestParam("file") MultipartFile file, @PathVariable Long pk_sponsor, @RequestParam String name, int telephone, String address, String site, String email, String description, int is_organization) {
        Sponsor sponsor = svc.sponsor(pk_sponsor);
        svc.updateSponsor(pk_sponsor, name, telephone, address, site, email, description, is_organization);
        addImage(file, "sponsors", sponsor.getPk_sponsor());
        return "redirect:/sponsors/{pk_sponsor}";
    }

    //get animal form client
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/json")
    public Object descIndex(String species, String name, String breed, String gender, String age, String description, String weight, String sterilize, String image, String phone) throws IOException {
        int animalGender = Integer.parseInt(gender);
        int animalAge = Integer.parseInt(age);
        float animalWeight = Float.parseFloat(weight);
        int animalSterilize = Integer.parseInt(sterilize);

        Type_Animal animalType = svc.findAnimalPK(species);

        Long pk_type_animal = animalType.getPk_type_animal();
        Animal animal = svc.addAnimal(pk_type_animal, name, typeHomeless, animalGender, animalWeight, breed, animalAge, description, animalSterilize, isNotApproved, phone);
        //Animal animal = svc.addAnimal(pk_type_animal, name, null, animalGender, null, null, 2.5f, null, null, 25, null, 1, isNotApproved);
        System.out.println("Phone:" + phone);
        log.info(animal.getPk_animal());
        //Volunteer volunteer = svc.addVolunteer(null, null, 123, null, null, description);
        String myPath = "C:\\testIMG\\static\\img" + File.separator + "temp_animals\\";

        File dir = new File(myPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        byte[] data = Base64.decodeBase64(image);
        try (OutputStream stream = new FileOutputStream(myPath + animal.getPk_animal() + ".jpg")) {
            log.info("created:" + stream.toString());
            stream.write(data);
        }
        notification();

        return null;

    }

    //get lost animal form client
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/lost_animal")
    public Object receiveLostAnimal(String date, String location, String species, String description, String image, String phone, String latitude, String longitude) throws IOException {

        Type_Animal animalType = svc.findAnimalPK(species);

        Long pk_type_animal = animalType.getPk_type_animal();
        //Animal animal = svc.addAnimal(pk_type_animal, null, typeLost, 0, 0, null, 0, description, 0, isNotApproved, phone);
        Animal animal = svc.addLostAnimal(pk_type_animal, typeLost, description, phone, date, location, latitude, longitude);
        //Animal animal = svc.addAnimal(pk_type_animal, name, null, animalGender, null, null, 2.5f, null, null, 25, null, 1, isNotApproved);
        System.out.println("Phone:" + phone);
        log.info(animal.getPk_animal());
        //Volunteer volunteer = svc.addVolunteer(null, null, 123, null, null, description);
        String myPath = "C:\\testIMG\\static\\img" + File.separator + "temp_animals\\";

        File dir = new File(myPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        byte[] data = Base64.decodeBase64(image);
        try (OutputStream stream = new FileOutputStream(myPath + animal.getPk_animal() + ".jpg")) {
            log.info("created:" + stream.toString());
            stream.write(data);
        }
        notification();

        return null;

    }

    private static final String extension = ".jpg";
    private final String myPath = "C:\\testIMG\\static\\img\\";

    String tmpfolder;

    private void addImage(MultipartFile file, String folderName, Long pk) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                String folder = myPath + folderName + File.separator;
                File dir = new File(folder);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                log.info("Foldername: " + folder);
                // Create the file on server
                File fileImage = new File(folder + pk + extension);
                log.info("File: " + fileImage);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(fileImage));

                stream.write(bytes);

                stream.close();
                //resize Uploaded image
                if (folderName.equals("animals")) {
                    makeThumbnails(fileImage, folder, pk);
                }

            } catch (Exception e) {
                //log.info("Server File Location=" + serverFile.getAbsolutePath());
                log.info("You failed to upload " + tmpfolder + " => " + e.getMessage());
                log.info("Foldername: " + folderName);
            }
        }
    }

    private void makeThumbnails(File fileImg, String folder, Long pk) throws IOException {
        BufferedImage bi = ImageIO.read(fileImg);
        BufferedImage resizedImage = Scalr.resize(bi, 150);
        File outputImage = new File(folder + File.separator + "thumbnails" + File.separator + pk + extension);
        if (!outputImage.exists()) {
            outputImage.mkdirs();
        }
        log.info("File thumb:" + outputImage);
        ImageIO.write(resizedImage, "jpg", outputImage);
    }

    public String getProgramPath2() throws UnsupportedEncodingException {
        URL url = MyController.class.getProtectionDomain().getCodeSource().getLocation();
        String jarPath = URLDecoder.decode(url.getPath(), "UTF-8");

        String parentPath = new File(jarPath).getParentFile().getPath();
        log.info("jarPath" + parentPath);
        return parentPath;
    }

    private void deleteImage(String folderName, Long pk) {
        String folder = myPath + folderName; //  C:\\testIMG\\static\\img\\foldername
        File file = new File(folder + File.separator + pk + extension); //  C:\\testIMG\\static\\img\\foldername\\pk.jpg

        if (file.delete()) {
            log.info(file.getAbsolutePath() + " is Deleted");
        } else {

            log.info(file.getAbsolutePath() + " Delete operation is failed.");
        }
        if (folderName.equals("animals")) {
            String folderThumbnail = myPath + folderName + File.separator + "thumbnails"; // C:\\testIMG\\static\\img\\foldername\\thumbnails
            File fileThumbnail = new File(folderThumbnail + File.separator + pk + extension); // C:\\testIMG\\static\\img\\foldername\\thumbnails\\pk
            if (fileThumbnail.delete()) {
                log.info(fileThumbnail.getAbsolutePath() + " Thumbnail is Deleted");
            } else {

                log.info(fileThumbnail.getAbsolutePath() + " Thumbnail is Delete operation is failed.");
            }
        }

    }

    private void replaceImage(String source, String target) throws IOException {
        File fileFrom = new File(source);
        File fileTo = new File(target);
        Files.move(fileFrom.toPath(), fileTo.toPath(), StandardCopyOption.REPLACE_EXISTING);

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("static/**")
                .addResourceLocations("file:/C:/testIMG/static");
        super.addResourceHandlers(registry);
    }
}
