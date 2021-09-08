import './styles/CardProfile.css';

export default class CardProfile {
    constructor(name){

        this.name = name;


        this.src = "img/profiles/" + name + ".jpg";
    }

    render() {
        return (
            <div className = "Card">
                <center>
                    <img className="Profile_Image" src = {this.src} />
                    <h3 className="family_title kerning_s font_s">{this.name}</h3>
                </center>
            </div>
        );
    }
}