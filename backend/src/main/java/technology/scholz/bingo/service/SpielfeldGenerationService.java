package technology.scholz.bingo.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import technology.scholz.bingo.domain.SpielSettings;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class SpielfeldGenerationService {

    @Autowired
    private RandomService randomService;

    public List<List<Integer>> generateSpielfeld(SpielSettings spielSettings) {

        List<Integer> verfuegbareNummern = spielSettings.assembleAllNummern();

        if (spielSettings.getSpielfeldDimensions() != 2) {
            throw new IllegalArgumentException("Only two dimensional games are supported right now");
        } else {

            return IntStream.range(0, spielSettings.getSpielfeldElementsPerDimension())
                    .mapToObj(i -> generateRow(verfuegbareNummern, spielSettings.getSpielfeldElementsPerDimension()))
                    .peek(verfuegbareNummern::removeAll)
                    .collect(Collectors.toList());

        }

    }

    private List<Integer> generateRow(List<Integer> verfuegbareNummern, int range) {

        return IntStream.range(0, range)
                .mapToObj(i ->{
                    Integer selectedNumber = randomService.getRandomListItem(verfuegbareNummern);
                    verfuegbareNummern.remove(selectedNumber);
                    return selectedNumber;
                })
                .collect(Collectors.toList());
    }
}
