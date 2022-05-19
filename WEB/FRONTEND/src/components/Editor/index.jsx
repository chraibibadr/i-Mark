import "./index.css"

function Editor({onAnnotationChange,value,onSubmitAnnotation,pos}) {
  return (
    <>
    {pos.x ? (
    <div className="editorContainer " style={{position:"absolute",left:pos.x,top:pos.y}}>
        <div className="inner">
        <textarea
          placeholder="Ajouter Annotation"
          onChange={onAnnotationChange}
          value={value}
        ></textarea>
        {value && <button className="editorBtn" onClick={onSubmitAnnotation}>Ajouter</button>}
        </div>
    </div>) : ""
    }
    </>
  )
}

export default Editor