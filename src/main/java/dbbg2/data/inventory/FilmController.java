package dbbg2.data.inventory;

public class FilmController {
    private Film changeFilm;

    public void setFilm(Film film) {
        changeFilm = film;
    }

    public void amendInformationFilm(String title, int agelimit, String director, String originCountry) {
        changeFilm.setTitle(title);
        changeFilm.setAgeLimit(agelimit);
        changeFilm.setDirector(director);
        changeFilm.setOriginCountry(originCountry);

    }
}
