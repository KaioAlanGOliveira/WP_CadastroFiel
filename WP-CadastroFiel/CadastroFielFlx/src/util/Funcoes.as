package util
{
	import mx.controls.Alert;

	public class Funcoes
	{
		public function Funcoes()
		{
		}
		
		public static function showMenssagemErro(mensagem :String) :void 
		{
			var mensagemAlterada :String = mensagem
				.replace("java.lang.Exception : ","");
			
			Alert.show(mensagemAlterada, "Opa!"); 
		}
	}
}