import "./styles/TriangleSection.css"

<<<<<<< HEAD
export function TriangleSection(a, b) {
    if (b < 1) {
        return ( <
            div id = "InnerDiv" >
            <
            div id = "TrapezoidShape1" >
            <
            center >
            <
            h1 > { a } < /h1>   <
            /center >  <
            /div>   <
            /div>
        );
    } else {
        return ( <
            div id = "InnerDiv" >
            <
            div id = "TrapezoidShape2" >
            <
            center >
            <
            h1 id = "h1Bello" > { a } < /h1>   <
            /center >  <
            /div>   <
            /div>
        );
    }
=======
export function TriangleSection(a) {
    return ( 
        <div id = "InnerDiv">
            <div id = "TrapezoidShape">
                <center>
                    <h1> { a } </h1>  
                </center> 
            </div>
        </div>
    );
>>>>>>> a5a0304b7bb24d8587859e3c61c37046a379a5ec
}

export default TriangleSection;