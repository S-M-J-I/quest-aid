package com.project.questaidbackend.services;

import com.project.questaidbackend.exceptions.EntityNotFoundException;
import com.project.questaidbackend.models.Club;
import com.project.questaidbackend.models.ClubDepartment;
import com.project.questaidbackend.repository.ClubRepository;
import com.project.questaidbackend.services.interfaces.IClubDepartmentService;
import com.project.questaidbackend.services.interfaces.IClubService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClubServicesImpl implements IClubService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ClubRepository clubRepository;
    private IClubDepartmentService clubDepartmentService;

    @Override
    public Club getClubByName(String name) {
        return unwrapClub(clubRepository.findByClubName(name), 404L);
    }

    @Override
    public Club createClub(Club club) {
        club.setPassword(bCryptPasswordEncoder.encode(club.getPassword()));
        Club savedClub = clubRepository.save(club);

        // create a General Department
        clubDepartmentService.addDepartment(new ClubDepartment(club, "General"), savedClub);
        return savedClub;
    }

    @Override
    public void removeClub(Club club) {
        clubRepository.delete(club);
    }

    @Override
    public void modifyClub(Club club) {
//        clubRepository.updateClub(club.getClubName(), club.getClubLogoPath(), club.getId());
    }

    @Override
    public Club getClub(Long id) {
        Optional<Club> club = clubRepository.findById(id);
        return unwrapClub(club, id);
    }

    @Override
    public Club getClubByEmail(String email) {
        Optional<Club> club = clubRepository.findByEmail(email);
        return unwrapClub(club, 404L);
    }

    @Override
    public ClubDepartment addDepartment(ClubDepartment department, Long clubId) {
        Club club = getClub(clubId);
        return clubDepartmentService.addDepartment(department, club);
    }

    static Club unwrapClub(Optional<Club> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, Club.class);
    }
}
