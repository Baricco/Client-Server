export default class SectionTemplate{

    constructor(bkg, height){
        this.bkg = bkg;
        this.height = height;
    }
    
    set bkgBefore(bkgB){
        this.bkgBefore = bkgB;
    } 
    set bkgAfter(bkgA){
        this.bkgAfter = bkgA;
    }

    render(){
        return (
            <div className="section">
                {this.content}
            </div>
        );
    }
}